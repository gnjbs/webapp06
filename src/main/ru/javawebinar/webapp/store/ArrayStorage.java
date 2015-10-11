package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * GKislin
 * 02.10.2015.
 */
public class ArrayStorage implements IStore {
    private static final int MAX_LENGTH = 10000;
    private static int currentSize = 0;
    private Resume[] array = new Resume[MAX_LENGTH];

    @Override
    public void clear() {
        Arrays.fill(array, null);
        currentSize = 0;
    }

    @Override
    public void save(Resume r) {
        requireNonNull(r);
        int index = getIndexByUuid(r.getUuid());
        if (index == -1) {
            array[currentSize++] = r;
            System.out.println("Сохранил");
        } else {
            System.err.println("Невозможно сохранить");
        }
    }

    @Override
    public void update(Resume r) {
        requireNonNull(r);
        int index = getIndexByUuid(r.getUuid());
        if (index == -1) {
            throw new NoSuchElementException();
        } else {
            array[index] = r;
        }
    }

    @Override
    public Resume load(String uuid) {
        requireNonNull(uuid);
        return array[getIndexByUuid(uuid)];
    }

    @Override
    public void delete(String uuid) {
        requireNonNull(uuid);
        int index = getIndexByUuid(uuid);
        if (index >= 0 && index < array.length) {
            System.arraycopy(array, index + 1, array, index, currentSize);
            currentSize--;
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public Collection<Resume> getAllSorted() {
        requireNonNull(array);
        Resume[] copy = Arrays.copyOf(array, currentSize);
        Arrays.sort(copy, 0, currentSize);
        return Arrays.asList(copy);

    }

    @Override
    public int size() {
        return currentSize;
    }

    private int getIndexByUuid(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (array[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
