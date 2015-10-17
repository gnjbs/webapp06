package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.exceptions.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ArturDS on 17.10.2015.
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();
    private ListIterator<Resume> listIterator = list.listIterator();

    @Override
    protected boolean exist(String uuid) {

        return false;
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
        //TODO Через итератор реализовать обновление
        while (listIterator.hasNext()) {

        }

    }

    @Override
    protected Resume doLoad(String uuid) {
        for (Resume resume : list) {
            if (uuid.equals(resume.getUuid())) {
                return resume;
            }
        }
        return null;
    }

    @Override
    protected void doDelete(String uuid) {
        while (listIterator.hasNext()) {
            if (uuid.equals(listIterator)) {
                listIterator.remove();
            }
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }
}
