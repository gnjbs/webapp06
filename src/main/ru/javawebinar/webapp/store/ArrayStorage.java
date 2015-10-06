package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 02.10.2015.
 */
public class ArrayStorage implements IStore {
    private static final int MAX_LENGTH = 10000;

    private Resume[] array = new Resume[MAX_LENGTH];

    @Override
    public void clear() {
        System.out.println("Лист очищен...");
        Arrays.fill(array, null);
    }

    @Override
    public void save(Resume r) {
        boolean isContains = isContains(array, r);
        boolean isContainsNull = isContains(array, null);

        if (!isContains && isContainsNull) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    array[i] = r;
                    break;
                }
            }
        } else if (isContains) {
            System.out.println("Добавление элемента невозможно");
        }
    }

    @Override
    public void update(Resume r) {
       boolean isContains = isContains(array, r);
        if (!isContains) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    if (r.getUuid().equals(array[i].getUuid())) {
                        array[i] = r;
                    }
                }
            }
        } else if (isContains) {
            this.save(r);        }
    }

    @Override
    public Resume load(String uuid) {

        for (Resume res : array)
            if (res != null) {
                if (uuid.equals(res.getUuid())) {
                    System.out.println("Нашел");
                    return res;
                }
            }
        return null;
    }

    @Override
    public void delete(String uuid) {

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                if (array[i].getUuid().equals(uuid)) {
                    array[i] = null;
                }
            }
        }
    }

    @Override
    public Collection<Resume> getAllSorted() {

        if (array != null) {
            List<Resume> list = new ArrayList<Resume>(Arrays.asList(array));
            list.removeAll(Collections.singleton(null));
            Collections.sort(list);
            return list;
        } else return null;
    }

    @Override
    public int size() {

        int size = 0;
        for (Resume r : array) {
            if (r != null) {
                size++;
            }
        }
        return size;
    }

    private boolean isContains(Resume[] array, Resume resume) {
        return Arrays.asList(array).contains(resume);
    }
}
