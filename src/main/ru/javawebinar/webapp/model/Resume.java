package main.ru.javawebinar.webapp.model;

import java.util.Map;
import java.util.UUID;

/**
 * GKislin
 * 18.09.2015.
 */
public class Resume {
    private final String uuid;
    private final Map<ContactType, String> contacts;
    private final Map<SectionType, Section> sections;

    public Resume(Map<ContactType, String> contacts, Map<SectionType, Section> sections) {
        this(UUID.randomUUID().toString(), contacts, sections);
    }

    public Resume(String uuid, Map<ContactType, String> contacts, Map<SectionType, Section> sections) {
        this.uuid = uuid;
        this.contacts = contacts;
        this.sections = sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }
//
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) {
            return false;
        }
        if (!contacts.equals(resume.contacts)) {
            return false;
        }
        return sections.equals(resume.sections);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }
}
