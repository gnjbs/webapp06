package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;

import static org.junit.Assert.*;

/**
 * Created by ArturDS on 14.10.2015.
 */
public class AbstractStorageTest {
    private Resume R1, R2, R3;
    private IStorage store = new SortedArrayStorage();

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
                                new Position(2005, Month.JANUARY, "position1", "content1"),
                                new Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoad() throws Exception {

    }
}