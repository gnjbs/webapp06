package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.util.*;
import java.util.stream.Collectors;



/**
 * GKislin
 * 16.10.2015.
 */
public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean exist(String uuid) {
        return false;
    }

    @Override
    protected void doClear() {
        map.clear();
    }

    @Override
    protected void doSave(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume r) {
        map.replace(r.getUuid(), r);
    }

    @Override
    protected Resume doLoad(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        map.remove(uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        return map.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return map.size();
    }
}
