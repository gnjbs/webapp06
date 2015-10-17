package ru.javawebinar.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ArturDS on 17.10.2015.
 */
public class MapStorageTest  extends AbstractStorageTest{
    public MapStorageTest() {
        super(new MapStorage());
    }
}