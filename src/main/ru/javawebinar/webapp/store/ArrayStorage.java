package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.Collection;

/**
 * GKislin
 * 02.10.2015.
 */
public class ArrayStorage implements IStore {
    private static final int MAX_LENGTH = 10000;
//Реалзовать методы
    private Resume[] array = new Resume[MAX_LENGTH];

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
}
