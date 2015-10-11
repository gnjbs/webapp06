package main.ru.javawebinar.webapp.util;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by ArturDS on 12.10.2015.
 */
public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    private DateUtil() {
    }

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
