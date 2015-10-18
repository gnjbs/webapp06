package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.*;

/**
 * Created by ArturDS on 17.10.2015.
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();

    @Override
    protected boolean exist(String uuid) {

        return getIndex(uuid) >= 0;
    }

    @Override
    protected void doClear() {
        list.clear();
    }

    @Override
    protected void doSave(Resume r) {
        list.add(r);
    }

    @Override
    protected void doUpdate(Resume r) {
        list.set(getIndex(r.getUuid()), r);
    }

    @Override
    protected Resume doLoad(String uuid) {
        return list.get(getIndex(uuid));
    }

    @Override
    protected void doDelete(String uuid) {
        list.remove(getIndex(uuid));
    }

    @Override
    protected List<Resume> doGetAll() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
