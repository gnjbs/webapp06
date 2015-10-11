package main.ru.javawebinar.webapp.model;

import main.ru.javawebinar.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;

import static main.ru.javawebinar.webapp.util.DateUtil.*;
import static main.ru.javawebinar.webapp.util.DateUtil.of;

/**
 * GKislin
 * 02.10.2015.
 */
public class Position {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Position(int startYear, Month startMonth, String title, String description) {
        this(of(startYear, startMonth), NOW, title, description);
    }

    public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        this(of(startYear, startMonth), of(endYear, endMonth), title, description);
    }

    public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;

        if (!startDate.equals(position.startDate)) {
            return false;
        }
        if (endDate != null ? !endDate.equals(position.endDate) : position.endDate != null) {
            return false;
        }
        if (!title.equals(position.title)) {
            return false;
        }
        return !(description != null ? !description.equals(position.description) : position.description != null);

    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}