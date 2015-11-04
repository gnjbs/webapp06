package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * GKislin
 * 23.10.2015.
 */
//TODO implements. Handle all IOException here
public abstract class AbstractFileStorage extends AbstractStorage<File>{
    @Override
    protected boolean exist(String uuid, File ctx) {
        return false;
    }

    @Override
    protected File getContext(String uuid) {
        return null;
    }

    @Override
    protected void doClear() {

    }

    @Override
    protected void doSave(Resume r, File ctx){


    }

    @Override
    protected void doUpdate(Resume r, File file) {
        //TODO или нужно ? сначало load, затем перезапись полей, потом save?
        Resume loadResume = doLoad(r.getUuid(), file);
        if (loadResume.equals(r)) {
        } else {
            doDelete(r.getUuid(), file);
            doSave(r, file);
        }
    }

    @Override
    protected Resume doLoad(String uuid, File ctx) {
        return null;
    }

    @Override
    protected void doDelete(String uuid, File ctx) {

    }

    @Override
    protected List<Resume> doGetAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
