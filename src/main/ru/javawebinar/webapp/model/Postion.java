package main.ru.javawebinar.webapp.model;

import java.time.LocalDate;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class Postion {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Postion(LocalDate startDate, LocalDate endDate, String title) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }
}
