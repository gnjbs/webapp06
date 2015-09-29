package main.ru.javawebinar.webapp.model;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class Section {
    private String type;

    public Section(String type) {
        this.type = type;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
