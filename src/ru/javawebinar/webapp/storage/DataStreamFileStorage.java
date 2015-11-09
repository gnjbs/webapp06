package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.exceptions.ExceptionType;
import ru.javawebinar.webapp.exceptions.WebAppException;
import ru.javawebinar.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GKislin
 * 23.10.2015.
 */
public class DataStreamFileStorage extends AbstractFileStorage {


    public DataStreamFileStorage(String path) {
        super(path);
    }

    @Override
    protected void write(Resume r, OutputStream os) {

    }

    @Override
    protected Resume read(InputStream is) {
        return null;
    }

    @Override
    protected Resume doLoad(String uuid, File ctx) {
        return null;
    }
}
