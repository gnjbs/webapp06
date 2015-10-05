package main.ru.javawebinar.webapp.store;

import main.ru.javawebinar.webapp.model.Resume;

import java.util.*;

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
        array = null;
    }

    @Override
    public void save(Resume r) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = r;
                break;
            }
        }
    }

    @Override
    public void update(Resume r) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                if (array[i].getUuid().equals(r.getUuid())) {
                    array[i] = r;
                }
            }/* else if (array[i] == null) {
                break;
            }*/
        }
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
            } else if (array[i] == null) {
                break;
            }
        }
    }

    @Override
    public Collection<Resume> getAllSorted() {
        try {
            ArrayList<Resume> arrayAsList = new ArrayList<>();
            arrayAsList.clear();
            for (Resume r : array) {
                if (r != null) {
                    arrayAsList.add(r);
                }
            }
            Collections.sort(arrayAsList);
            return arrayAsList;

        } catch (NullPointerException e) {
            e.getLocalizedMessage();
        }

        return null;
    }

    @Override
    public int size() {
        //также возможно array.length
        int size = 0;
        for (Resume r : array) {
            if (r != null) {
                size++;
            }
        }
        return size;
    }
}
