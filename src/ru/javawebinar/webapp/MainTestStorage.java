package ru.javawebinar.webapp;

import ru.javawebinar.webapp.model.*;
import ru.javawebinar.webapp.storage.ArrayStorage;
import ru.javawebinar.webapp.storage.IStorage;

import java.io.IOException;
import java.time.Month;
import java.util.logging.LogManager;

/**
 * GKislin
 * 08.10.2015.
 */
public class MainTestStorage {
    private Resume R1, R2, R3;
    private IStorage store = new ArrayStorage();

    {
        R1 = new Resume("Полное Имя1");
        R1.addContact(ContactType.MAIL, "mail1@ya.ru");
        R1.addContact(ContactType.PHONE, "11111");
        R2 = new Resume("Полное Имя2");
        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "22222");
        R3 = new Resume("Полное Имя3");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.ACHIEVEMENT, new MultiTextSection("Achivment11", "Achivment12"));
        R1.addSection(SectionType.QUALIFICATIONS, new MultiTextSection("Java", "SQL"));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", null,
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
    }

    private void test() {
        store.save(R1);
        store.save(R2);
        store.save(R3);
        assertGetResume(R1);
        assertGetResume(R2);
        assertGetResume(R3);
        assertTrue(store.size() == 3);
        store.delete(R1.getUuid());
        assertTrue(store.size() == 2);
//        assertTrue(store.getAllSorted().equals(Arrays.asList(R2, R3)));
        store.save(R1);
//        assertTrue(store.getAllSorted().equals(Arrays.asList(R1, R2, R3)));
        assertGetResume(R1);
        assertGetResume(R2);
        assertGetResume(R3);
        System.out.println(store.getAllSorted());
    }

    private void assertGetResume(Resume r) {
        assertTrue(store.load(r.getUuid()).equals(r));
    }

    private void assertTrue(boolean value) {
        if (!value) {
            throw new AssertionError();
        }
    }

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(
                MainTestStorage.class.getResourceAsStream("/logging.properties"));
        MainTestStorage mainTestStorage = new MainTestStorage();
        mainTestStorage.test();
    }
}
