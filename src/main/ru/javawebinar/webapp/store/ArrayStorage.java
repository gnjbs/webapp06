package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.*;

/**
 * GKislin
 * 02.10.2015.
 */
public class ArrayStorage implements IStore {
    private static final int MAX_LENGTH = 10000;

    private Resume[] array = new Resume[MAX_LENGTH];

    @Override
    public void clear() {
        System.out.println("Лист очищен...");
        Arrays.fill(array, null);
    }

    @Override
    public void save(Resume r) {
        int contain = isContains(array, r);
        if (contain == -1) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    array[i] = r;
                    System.out.println("Объект " + r.toString() + " сохранен в массиве");
                    break;
                }
            }
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }

    }

    @Override
    public void update(Resume r) {
        int contain = isContains(array, r);
        if (contain > -1) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    if (r.getUuid().equals(array[i].getUuid())) {
                        array[i] = r;
                    }
                }
            }

        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public Resume load(String uuid) {

        for (Resume res : array)
            if (res != null) {
                if (uuid.equals(res.getUuid())) {
                    System.out.println("Нашел");
                    return res;
                }
            }
        return null;
    }

    @Override
    public void delete(String uuid) {


        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                if (array[i].getUuid().equals(uuid)) {
                    array[i] = null;
                }
            }
        }
    }

    @Override
    public Collection<Resume> getAllSorted() {

        if (array != null) {
            List<Resume> list = new ArrayList<Resume>(Arrays.asList(array));
            list.removeAll(Collections.singleton(null));
            Collections.sort(list);
            return list;
        } else return null;
    }

    @Override
    public int size() {

        int size = 0;
        for (Resume r : array) {
            if (r != null) {
                size++;
            }
        }
        return size;
    }

    private int isContains(Resume[] array, Resume resume) {
        for (int i = 0; i < array.length; i++) {
            if (resume.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
