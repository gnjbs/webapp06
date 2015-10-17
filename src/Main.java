import ru.javawebinar.webapp.model.Resume;
import ru.javawebinar.webapp.storage.ListStorage;
import ru.javawebinar.webapp.storage.MapStorage;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: gkislin
 * Date: 18.06.2014
 */
public class Main {
    public static void main(String[] args) {

        MapStorage map = new MapStorage();
        ListStorage list = new ListStorage();
        map.save(new Resume("1", "Сергей Иванович"));
        map.save(new Resume("Андре Иванович"));
        map.save(new Resume("Валдис Иванович"));
        list.save(new Resume(""));
        //map.delete("1");
        List<Resume> list2 = (List<Resume>) map.getAllSorted();
        System.out.println(list);
    }
}
