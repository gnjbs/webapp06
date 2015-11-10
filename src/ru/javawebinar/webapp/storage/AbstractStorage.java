package ru.javawebinar.webapp.storage;

import ru.javawebinar.webapp.exceptions.ExceptionType;
import ru.javawebinar.webapp.exceptions.WebAppException;
import ru.javawebinar.webapp.model.Resume;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * GKislin
 * 09.10.2015.
 */
public abstract class AbstractStorage<C> implements IStorage {
    //    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected final Logger log = Logger.getLogger(getClass().getName());

    protected abstract boolean exist(C ctx);

    @Override
    public void clear() {
        log.info("clear");
        doClear();
    }

    protected C getContext(Resume r) {
        return getContext(r.getUuid());
    }

    protected abstract C getContext(String uuid);

    protected abstract void doClear();

    @Override
    public void save(Resume r) {
        log.info("Save " + r);
        requireNonNull(r, "Resume must not be null");
        C ctx = getContext(r);
        mustNotExist(r.getUuid(), ctx);
        doSave(r, ctx);
    }

    protected abstract void doSave(Resume r, C ctx);

    @Override
    public void update(Resume r) {
        log.info("Update " + r);
        requireNonNull(r, "Resume must not be null");
        C ctx = getContext(r);
        mustExist(r.getUuid(), ctx);
        doUpdate(r, ctx);
    }

    protected abstract void doUpdate(Resume r, C ctx);

    @Override
    public Resume load(String uuid) {
        log.info("Load " + uuid);
        requireNonNull(uuid, "UUID must not be null");
        C ctx = getContext(uuid);
        mustExist(uuid, ctx);
        return doLoad(ctx);
    }

    protected abstract Resume doLoad(C ctx);

    @Override
    public void delete(String uuid) {
        log.info("Delete " + uuid);
        requireNonNull(uuid, "UUID must not be null");
        C ctx = getContext(uuid);
        mustExist(uuid, ctx);
        doDelete(ctx);
    }

    protected abstract void doDelete(C ctx);

    @Override
    public Collection<Resume> getAllSorted() {
        log.info("getAllSorted");
        List<Resume> list = doGetAll();
        Collections.sort(list, new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                return o1.getFullName().compareTo(o2.getFullName());
            }
        });
        return list;
    }

    protected abstract List<Resume> doGetAll();

    private void mustNotExist(String uuid, C ctx) {
        if (exist(ctx)) {
            throw new WebAppException(ExceptionType.ALREADY_EXISTS, uuid);
        }
    }

    private void mustExist(String uuid, C ctx) {
        if (!exist(ctx)) {
            throw new WebAppException(ExceptionType.NOT_FOUND, uuid);
        }
    }
}
