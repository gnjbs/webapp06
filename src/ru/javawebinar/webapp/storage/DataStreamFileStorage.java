package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.exceptions.ExceptionType;
import ru.javawebinar.webapp.exceptions.WebAppException;
import ru.javawebinar.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GKislin
 * 23.10.2015.
 */
public class DataStreamFileStorage extends AbstractFileStorage {

    protected final File directory;
    protected final String zero = "$%$";
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DataStreamFileStorage(String path) {
        directory = new File(path);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(path + " is not directory");
        }
    }

    @Override
    protected File getContext(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean exist(String uuid, File file) {
        return file.isFile();
    }

    @Override
    protected void doClear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file.getName(), file);
            }
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            if (!file.createNewFile()) {
                throw new WebAppException(ExceptionType.IO_ERROR, r.getUuid());
            }
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
                dos.writeUTF(r.getFullName());
                dos.writeInt(r.getContacts().size());
                for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue());
                }
                //Write Sections
                dos.writeInt(r.getSections().size());

                for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue().getClass().getSimpleName());
                    switch (entry.getValue().getClass().getSimpleName()) {
                        case "TextSection":
                            dos.writeUTF(((TextSection) entry.getValue()).getContent());
                            break;
                        case "MultiTextSection":
                            List<String> lines = ((MultiTextSection) entry.getValue()).getLines();
                            if (lines.size() > 0) {
                                dos.writeInt(lines.size());
                                for (String line : lines) {
                                    dos.writeUTF(line);
                                }
                            }
                            break;
                        case "OrganizationSection":
                            List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                            if (organizations.size() > 0) {
                                dos.writeInt(organizations.size());
                                for (Organization organization : organizations) {
                                    dos.writeUTF((organization.getHomePage().getName() == null)
                                            ? zero : organization.getHomePage().getName());
                                    dos.writeUTF((organization.getHomePage().getUrl() == null)
                                            ? zero : organization.getHomePage().getUrl());
                                    List<Organization.Position> positions = organization.getPositions();
                                    dos.writeInt(positions.size());
                                    for (Organization.Position position : positions) {
                                        dos.writeUTF(position.getStartDate() == null ? zero : position.getStartDate().toString());
                                        dos.writeUTF(position.getEndDate() == null ? zero : position.getEndDate().toString());
                                        dos.writeUTF(position.getTitle() == null ? zero : position.getTitle());
                                        dos.writeUTF(position.getDescription() == null ? zero : position.getDescription());
                                    }
                                }
                            }
                            break;
                    }
                }

            }
            //TODO implements section
        } catch (IOException e) {
            throw new WebAppException(ExceptionType.IO_ERROR, r.getUuid());
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        //TODO или нужно ? сначало load, затем перезапись полей, потом save?
        Resume loadResume = doLoad(r.getUuid(), file);
        if (loadResume.equals(r)) {
        } else {
            doDelete(r.getUuid(), file);
            doSave(r, file);
        }


    }

    @Override
    protected Resume doLoad(String uuid, File file) {
        try {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                String fullName = dis.readUTF();
                int contactSize = dis.readInt();
                Resume r = new Resume(uuid, fullName);
                for (int i = 0; i < contactSize; i++) {
                    r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
                }
                int sectionSize = dis.readInt();
                for (int i = 0; i < sectionSize; i++) {
                    String sectionType = dis.readUTF();
                    String section = dis.readUTF();
                    switch (section) {
                        case "TextSection":
                            r.addSection(SectionType.valueOf(sectionType), new TextSection(dis.readUTF()));
                            break;
                        case "MultiTextSection":
                            int numberOfMultiTextSection = dis.readInt();
                            List<String> mtsList = new ArrayList<>();
                            for (int j = 0; j < numberOfMultiTextSection; j++) {
                                mtsList.add(dis.readUTF());
                            }
                            r.addSection(SectionType.valueOf(sectionType), new MultiTextSection(mtsList));
                            break;
                        case "OrganizationSection":
                            int numberOfOrganization = dis.readInt();
                            Organization[] organizations = new Organization[numberOfOrganization];
                            Organization.Position[] positions = null;
                            for (int j = 0; j < numberOfOrganization; j++) {
                                String organizationName = isNull(dis.readUTF());
                                String organizationURL = isNull(dis.readUTF());
                                int numberOfPositions = dis.readInt();
                                if (numberOfPositions > 0) {
                                    positions = new Organization.Position[numberOfPositions];
                                    for (int k = 0; k < numberOfPositions; k++) {
                                        LocalDate startDate = LocalDate.parse(dis.readUTF(), formatter);
                                        LocalDate endDate = LocalDate.parse(dis.readUTF(), formatter);
                                        String positionTitle = isNull(dis.readUTF());
                                        String positionDescription = isNull(dis.readUTF());
                                        positions[k] = new Organization.Position(startDate, endDate, positionTitle, positionDescription);
                                    }
                                } else if (numberOfPositions == 0) {
                                    positions = null;
                                }

                                if (positions == null) {
                                    organizations[j] = new Organization(organizationName, organizationURL);
                                } else {
                                    organizations[j] = new Organization(organizationName, organizationURL, positions);
                                }

                            }
                            r.addSection(SectionType.valueOf(sectionType), new OrganizationSection(organizations));
                            break;
                    }
                }
                //TODO implements section
                return r;
            }
        } catch (IOException e) {
            throw new WebAppException(ExceptionType.IO_ERROR, uuid);
        }
    }

    @Override
    protected void doDelete(String uuid, File file) {
        if (!file.delete()) {
            throw new WebAppException(ExceptionType.IO_ERROR, uuid);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            list.add(doLoad(files[i].getName(), files[i]));
        }
        return list;
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        return files.length;
    }

    public String isNull(String something) {
        if (something.equals(zero)) {
            something = null;

        }
        return something;
    }
}
