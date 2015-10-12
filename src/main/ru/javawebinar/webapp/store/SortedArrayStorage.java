package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by ArturDS on 12.10.2015.
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    //Здесь двоичный поиск по отсортированному.

    private final Resume[] array = new Resume[MAX_LENGTH];
    private final String[] sortedUuids = new String[MAX_LENGTH];
    private int currentSize = 0;

    @Override
    public void clear() {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public Resume load(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Collection<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    private int getIndex(String uuid) {
        return Arrays.binarySearch(sortedUuids, 0, currentSize, uuid);
    }
}
