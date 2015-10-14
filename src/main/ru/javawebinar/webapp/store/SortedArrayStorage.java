package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;

/**
 * GKislin
 * 09.10.2015.
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private final Resume[] array = new Resume[MAX_LENGTH];
    private String[] sortedUuids = new String[MAX_LENGTH];

    private int currentSize = 0;

    @Override
    public void clear() {
        Arrays.fill(array, null);  // let gc do his work
        Arrays.fill(sortedUuids, null);
        currentSize = 0;
    }

    @Override
    public void save(Resume r) {
        int idx = getIndexByUuid(r.getUuid());
        if (idx > 0) {
            throw new IllegalStateException("Resume " + r.getUuid() + "already exist");
        }
        if (currentSize == MAX_LENGTH) {
            throw new IllegalStateException("Max storage volume " + MAX_LENGTH + " is exceeded");
        }
//        http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        int insertIdx = -idx - 1;
        System.arraycopy(array, insertIdx, array, insertIdx + 1, currentSize - insertIdx);
        System.arraycopy(sortedUuids, insertIdx, sortedUuids, insertIdx + 1, currentSize - insertIdx);
        set(insertIdx, r);
        currentSize += 1;
    }

    private void set(int idx, Resume r) {
        array[idx] = r;
        sortedUuids[idx] = (r == null ? null : r.getUuid());
    }

    @Override
    public void update(Resume r) {
        set(getExistedIndex(r.getUuid()), r);
    }

    @Override
    public Resume load(String uuid) {
        return array[getExistedIndex(uuid)];
    }

    @Override
    public void delete(String uuid) {
        int idx = getExistedIndex(uuid);
        int numMoved = currentSize - idx - 1;
        if (numMoved > 0) {
            System.arraycopy(array, idx + 1, array, idx, numMoved);
            System.arraycopy(sortedUuids, idx + 1, sortedUuids, idx, numMoved);
        }
        set(--currentSize, null);
    }

    @Override
    public Collection<Resume> getAllSorted() {
        // TODO implement after collections do sort
        Resume[] copy = Arrays.copyOf(array, currentSize);
        return Arrays.asList(copy);
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    protected int getIndexByUuid(String uuid) {
        return Arrays.binarySearch(sortedUuids, 0, currentSize, uuid);
    }
}
