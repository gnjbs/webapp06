package main;

import main.ru.javawebinar.webapp.model.*;
import main.ru.javawebinar.webapp.store.ArrayStorage;


import java.util.HashMap;
import java.util.Map;

/**
 * User: gkislin
 * Date: 18.06.2014
 */
public class Main {
    public static void main(String[] args) {
        ArrayStorage arrayStorage = new ArrayStorage();
        Map<ContactType, String> testMapContact = new HashMap<>();
        testMapContact.put(ContactType.HOME_PHONE, "525757");
        testMapContact.put(ContactType.MAIL, "at@ya.ru");
        Map<SectionType, Section> testMapSection = new HashMap<>();
        Section educationSection = new TextSection("Учился где то...");
        testMapSection.put(SectionType.EDUCATION, educationSection);
        Resume resume = new Resume("Артур Сергеевич");

        Map<ContactType, String> testMapContact1 = new HashMap<>();
        testMapContact1.put(ContactType.HOME_PHONE, "5255235757");
        testMapContact1.put(ContactType.MAIL, "t@ya.ru");
        Map<SectionType, Section> testMapSection1 = new HashMap<>();
        Section educationSection1 = new TextSection("Достиг того-то");
        testMapSection1.put(SectionType.ACHIEVEMENT, educationSection1);
        Resume resume1 = new Resume("Иван Иванович");

        Map<ContactType, String> testMapContact2 = new HashMap<>();
        testMapContact2.put(ContactType.HOME_PHONE, "3232323");
        testMapContact2.put(ContactType.MAIL, "imnumber3@ya.ru");
        Map<SectionType, Section> testMapSection2 = new HashMap<>();
        Section educationSection2 = new TextSection("Весьма квалифицирован");
        testMapSection2.put(SectionType.QUALIFICATIONS, educationSection2);
        Resume resume2 = new Resume("Анатолий Михайлович");


        Resume resume3 = new Resume("resume2", "Армен");
        Resume resume4 = new Resume("Сергей Кирч");

        arrayStorage.save(resume);
        arrayStorage.save(resume1);
        arrayStorage.save(resume2);
        arrayStorage.save(resume4);
        arrayStorage.save(resume);

        System.out.println("Размер массива = " + arrayStorage.size());

        System.out.println("load резюме с UUID = resume2 = " + arrayStorage.load("resume2"));

        System.out.println("Текущий размер списка" + arrayStorage.size() + " Весь список \n" + arrayStorage.getAllSorted());

        arrayStorage.update(resume3);


        System.out.println("Весь список после обновления \n" + arrayStorage.getAllSorted());
        arrayStorage.delete("resume2");
        System.out.println("Размер списка после удаления элемента равен " + arrayStorage.size()
                + " Весь список после удаления \n" + arrayStorage.getAllSorted());
        arrayStorage.clear();
        arrayStorage.save(resume);
        arrayStorage.save(resume1);
        System.out.println(arrayStorage.getAllSorted());
    }
}
