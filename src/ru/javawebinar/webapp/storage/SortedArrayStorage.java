package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.Arrays;

/**
 * GKislin
 * 09.10.2015.
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private String[] sortedUuids = new String[MAX_LENGTH];

    @Override
    public void doClear() {
        Arrays.fill(sortedUuids, 0, currentSize, null);
        super.doClear();
    }

    @Override
    protected void set(int idx, Resume r) {
        array[idx] = r;
        sortedUuids[idx] = (r == null ? null : r.getUuid());
    }

    @Override
    public void doSave(Resume r, Integer idx) {
        checkExceeded(r);
//        http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        int insertIdx = -idx - 1;
        System.arraycopy(array, insertIdx, array, insertIdx + 1, currentSize - insertIdx);
        System.arraycopy(sortedUuids, insertIdx, sortedUuids, insertIdx + 1, currentSize - insertIdx);
        set(insertIdx, r);
        currentSize++;
    }

    @Override
    public void doDelete(Integer idx) {
        int numMoved = currentSize - idx - 1;
        if (numMoved > 0) {
            System.arraycopy(array, idx + 1, array, idx, numMoved);
            System.arraycopy(sortedUuids, idx + 1, sortedUuids, idx, numMoved);
        }
        set(--currentSize, null);
    }

    @Override
    protected Integer getContext(String uuid) {
        return Arrays.binarySearch(sortedUuids, 0, currentSize, uuid);
    }
}