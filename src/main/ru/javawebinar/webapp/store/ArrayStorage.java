package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.*;

/**
 * GKislin
 * 02.10.2015.
 */
public class ArrayStorage implements IStore {
    private static final int MAX_LENGTH = 10000;
    private static int size = 0;
    private Resume[] array = new Resume[MAX_LENGTH];

    @Override
    public void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        int index = getIndexByUuid(r.getUuid());
        if (index == -1) {
            array[size] = r;
            size++;
        } else {
            System.err.println("Невозможно сохранить");
        }
    }

    @Override
    public void update(Resume r) {
        int index = getIndexByUuid(r.getUuid());
        if (index == -1) {
            throw new NoSuchElementException();
        } else {
            array[index] = r;
        }
    }

    @Override
    public Resume load(String uuid) {
        int index = getIndexByUuid(uuid);
        if (index >= 0) {
            return array[index];
        } else {
            return null;
        }
    }

    @Override
    public void delete(String uuid) {

        int index = getIndexByUuid(uuid);
        if (index >= 0 && index < array.length) {
            System.arraycopy(array, index + 1, array, index, size);
            size--;
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public Collection<Resume> getAllSorted() {
        if (array != null) {
            Resume[] copy = Arrays.copyOf(array, size);
            Arrays.sort(copy, 0, size);
            return Arrays.asList(copy);
        } else return null;
    }

    @Override
    public int size() {
        return size;
    }

    private int getIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(array[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
