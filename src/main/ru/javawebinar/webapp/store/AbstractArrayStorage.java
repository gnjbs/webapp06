package main.ru.javawebinar.webapp.store;

/**
 * Created by ArturDS on 12.10.2015.
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int MAX_LENGTH = 10000;
    //TODO Сюда вынест все вещи которые относятся к массиву.
    protected int getExistIndex(String uuid) {
        int index = getIndexByUuid(uuid);
        if (index == -1) {
            throw new IllegalArgumentException("This uuid (" + uuid + ")is not exist");
        }
        return index;
    }

    protected abstract int getIndexByUuid(String uuid);
}
