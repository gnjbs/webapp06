package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.exceptions.ExceptionType;
import ru.javawebinar.webapp.exceptions.WebAppException;
import ru.javawebinar.webapp.model.*;

import java.io.*;
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
                    switch (entry.getKey()) {
                        case OBJECTIVE:
                            dos.writeUTF("OBJECTIVE");
                            break;
                        case ACHIEVEMENT:
                            dos.writeUTF("ACHIEVEMENT");
                            break;
                        case QUALIFICATIONS:
                            dos.writeUTF("QUALIFICATIONS");
                            break;
                        case EXPERIENCE:
                            dos.writeUTF("EXPERIENCE");
                            break;
                        case EDUCATION:
                            dos.writeUTF("EDUCATION");
                            break;
                    }
                    dos.writeUTF(entry.getValue().getClass().getSimpleName());
                    if ((TextSection.class) == (entry.getValue().getClass())) {
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                    }
                    if ((MultiTextSection.class) == (entry.getValue().getClass())) {
                        List<String> lines = ((MultiTextSection) entry.getValue()).getLines();
                        if (lines.size() > 0) {
                            dos.writeInt(lines.size());
                            for (String line : lines) {
                                dos.writeUTF(line);
                            }
                        }
                    }
                    if ((OrganizationSection.class) == (entry.getValue().getClass())) {
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                        if (organizations.size() > 0) {
                            for (Organization organization : organizations) {
                                dos.writeInt(organizations.size());
                                dos.writeUTF((organization.getHomePage().getName() == null)
                                        ? zero : organization.getHomePage().getName());
                                dos.writeUTF((organization.getHomePage().getUrl() == null)
                                        ? zero : organization.getHomePage().getUrl());
                                List<Organization.Position> positions = organization.getPositions();
                                if (positions.size() > 0) {
                                    for (Organization.Position position : positions) {
                                        dos.writeInt(positions.size());
                                        dos.writeUTF(position.getStartDate() == null ? zero : position.getStartDate().toString());
                                        dos.writeUTF(position.getEndDate() == null ? zero : position.getEndDate().toString());
                                        dos.writeUTF(position.getTitle() == null ? zero : position.getTitle());
                                        dos.writeUTF(position.getDescription() == null ? zero : position.getDescription());
                                    }
                                }
                            }
                        }
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
                            for (int j = 0; j < numberOfMultiTextSection; j++) {;
                                mtsList.add(dis.readUTF());

                            }
                            r.addSection(SectionType.valueOf(sectionType), new MultiTextSection(mtsList));
                            break;
                        case "OrganizationSection":

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
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

}
