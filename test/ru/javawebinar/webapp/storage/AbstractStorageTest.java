package ru.javawebinar.webapp.storage;

import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.rules.ExpectedException;
import ru.javawebinar.webapp.exceptions.WebAppException;
import ru.javawebinar.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * GKislin
 * 09.10.2015.
 */
public abstract class AbstractStorageTest {
    private Resume R1, R2, R3;

    //    http://stackoverflow.com/questions/3404301/whats-wrong-with-overridable-method-calls-in-constructors
    private final IStorage storage;   // = getStorage(); bad!
//    protected abstract IStorage getStorage();

    public AbstractStorageTest(IStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
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
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private void assertGet(Resume r) {
        assertTrue(storage.load(r.getUuid()).equals(r));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }



    @Test
    public void testClear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void testUpdate() throws Exception {
        R2.setFullName("Updated N2");
        storage.update(R2);
        assertGet(R2);
    }

    @Test
    public void testLoad() throws Exception {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        exception.expect(WebAppException.class);
        storage.load("dummy");
    }

    @Test
    public void testDelete() throws Exception {
        exception.expect(WebAppException.class);
        storage.delete(R1.getUuid());
        assertSize(2);
        assertGet(R2);
        assertGet(R3);
        assertGet(R1);
    }

    @Test
    public void testGetAllSorted() throws Exception {
        List<Resume> list = Arrays.asList(R1, R2, R3);
        assertEquals(list, new ArrayList<>(storage.getAllSorted()));
    }

    @Test
    public void testSize() throws Exception {
        assertSize(3);
    }

    @Test
    public void testSaveAlreadyExist() throws Exception {
        exception.expect(WebAppException.class);
        storage.save(R1);
    }

    @Test
    public void testUpdateMissed() throws Exception {
        try {
            Resume resume = new Resume("fullName_U1", "location_U1");
            storage.update(resume);
        } catch (WebAppException e) {
            return;
        }
        fail();
    }

    @Test
    public void typeOfExceptionVerify()  {
      //  exception.expect(WebAppException.class);
        exception.expect(new CustomMatcher(WebAppException.class, "Illegal state"));
        throw new RuntimeException("Runtime exception occurred",
                new IllegalStateException("Illegal state"));
    }
}