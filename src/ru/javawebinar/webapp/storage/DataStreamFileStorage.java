package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 23.10.2015.
 */
public class DataStreamFileStorage extends AbstractFileStorage {


    public DataStreamFileStorage(String path) {
        super(path);
    }

    @Override
    protected void write(Resume r, OutputStream os) throws IOException {
        try (final DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeCollection(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((MultiTextSection) section).getLines(), s -> dos.writeUTF(s));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((OrganizationSection) section).getOrganizations(), organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            writeCollection(dos, organization.getPositions(), new ElementWriter<Organization.Position>() {
                                @Override
                                public void write(Organization.Position position) throws IOException {
                                    writeLocalDate(dos, position.getStartDate());
                                    writeLocalDate(dos, position.getEndDate());

                                }
                            });
                        });
                        break;
                }
            });

        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().ordinal());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), Month.values()[dis.readInt()], 1);
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume r = new Resume(dis.readUTF(), dis.readUTF());
            readItems(dis, () -> r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                r.addSection(sectionType, readSection(dis, sectionType, r));
            });
            return r;
        }


    }

    private Section readSection(DataInputStream dis, SectionType sectionType, Resume r) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
                new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new MultiTextSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readList(dis, () ->
                    new Organization(new Link(dis.readUTF(), dis.readUTF()),
                            readList(dis, () ->
                                    new Organization.Position(readLocalDate(dis),
                                            readLocalDate(dis),dis.readUTF(),dis.readUTF() )))
                ));
            default:
                throw new IllegalStateException();
        }
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private interface Processor {
        void process() throws IOException;
    }

    private <T> void readItems(DataInputStream dis, Processor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }

    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}
