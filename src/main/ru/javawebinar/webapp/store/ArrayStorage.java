package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * GKislin
 * 02.10.2015.
 */
public class ArrayStorage extends AbstractArrayStorage {
    private static int currentSize = 0;
    private final Resume[] array = new Resume[MAX_LENGTH];

    @Override
    public void clear() {
        currentSize = 0;
        Arrays.fill(array, null);//go gc, go
    }

    @Override
    public void save(Resume r) {
        requireNonNull(r);
        int index = getIndexByUuid(r.getUuid());
        if (index != -1) {
            throw new IllegalArgumentException("Resume with " + r.getUuid() + " is already exist");
        }
        if (currentSize == MAX_LENGTH) {
            System.err.println("Достигнут максимальный размер массива");
            //Реализовать свой экспшн здесь
        }
        array[currentSize++] = r;
    }

    @Override
    public void update(Resume r) {
        requireNonNull(r);
        int existIndex = getExistIndex(r.getUuid());
        array[existIndex] = r;
    }

    @Override
    public Resume load(String uuid) {
        requireNonNull(uuid);
        int indexByUuid = getIndexByUuid(uuid);
        return array[indexByUuid];
    }

    @Override
    public void delete(String uuid) {
        requireNonNull(uuid);
        int existIndex = getExistIndex(uuid);
        array[existIndex] = array[--currentSize];
        array[currentSize] = null;
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

    private int getExistIndex(String uuid) {
        int index = getIndexByUuid(uuid);
        if (index == -1) {
            throw new IllegalArgumentException("This uuid (" + uuid + ")is not exist");
        }
        return index;
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
