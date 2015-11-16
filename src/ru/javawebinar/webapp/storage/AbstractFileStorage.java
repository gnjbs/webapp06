package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.exceptions.ExceptionType;
import ru.javawebinar.webapp.exceptions.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GKislin
 * 23.10.2015.
 */
//TODO implements. Handle all IOException here
public abstract class AbstractFileStorage extends AbstractStorage<File> {

    protected final File directory;

    @Override
    protected void doClear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new WebAppException(ExceptionType.IO_ERROR, file.getName());
        }
    }


    public AbstractFileStorage(String path) {
        directory = new File(path);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(path + " is not directory");
        }
    }

    @Override
    protected File getContext(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean exist(File file) {
        return file.isFile();
    }


    @Override
    protected void doSave(Resume r, File file) {
        try {
            if (!file.createNewFile()) {
                throw new WebAppException(ExceptionType.IO_ERROR, r.getUuid());
            }

        } catch (IOException e) {
            throw new WebAppException(ExceptionType.IO_ERROR, r.getUuid());
        }
        doUpdate(r, file);
    }

    protected abstract void write(Resume r, OutputStream os) throws IOException;


    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            write(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new WebAppException(ExceptionType.IO_ERROR, r.getUuid(), e);
        }
    }

    @Override
    protected Resume doLoad(File file) {
        try {
            return read(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new WebAppException(ExceptionType.IO_ERROR, file.getName(), e);
        }
    }

    protected abstract Resume read(InputStream is) throws IOException;


    @Override
    protected List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new WebAppException(ExceptionType.IO_ERROR, null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doLoad(file));
        }
        return list;
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        return files.length;
    }
}
