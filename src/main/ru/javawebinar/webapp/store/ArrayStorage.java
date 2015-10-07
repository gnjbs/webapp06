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
        int index = getIndexResume(array, r);
        if (index == -1) {
            int size = this.size();
            array[size] = r;
        } else {
            throw new ArrayStoreException();
        }
    }

    @Override
    public void update(Resume r) {
        int index = getIndexResume(array, r);
        if (index == -1) {
            throw new NoSuchElementException();
        } else {
            array[index] = r;
        }
    }

    @Override
    public Resume load(String uuid) {
        int index = getIndexByUuid(array, uuid);
        if (index >= 0) {
            return array[index];
        } else {
            return null;
        }
    }

    @Override
    public void delete(String uuid) {

        int index = getIndexByUuid(array, uuid);
        if (index >= 0 && index < array.length) {
            Resume[] copy = new Resume[array.length - 1];
            System.arraycopy(array, 0, copy, 0, index);
            System.arraycopy(array, index + 1, copy, index, array.length - index - 1);
            array = copy;
        } else {
            throw new NoSuchElementException();
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
        if (array != null) {
            int size = 0;
            for (Resume r : array) {
                if (r != null) {
                    size++;
                }
            }
            return size;
        }
        return 0;
    }

    private int getIndexResume(Resume[] array, Resume resume) {
        for (int i = 0; i < array.length; i++) {
            if (resume.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    private int getIndexByUuid(Resume[] array, String uuid) {
        for (int i = 0; i < array.length; i++) {
            if (uuid.equals(array[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
