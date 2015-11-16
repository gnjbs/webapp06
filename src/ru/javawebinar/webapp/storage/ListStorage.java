package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> list = new ArrayList<>();

    @Override
    protected boolean exist(Integer idx) {
        return idx >= 0;
    }

    @Override
    protected Integer getContext(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doClear() {
        list.clear();
    }

    @Override
    protected void doSave(Resume r, Integer idx) {
        list.add(r);
    }

    @Override
    protected void doUpdate(Resume r, Integer idx) {
        list.set(idx, r);
    }

    @Override
    protected Resume doLoad(Integer idx) {
        return list.get(idx);
    }

    @Override
    protected void doDelete(Integer idx) {
        list.remove((idx).intValue());
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}