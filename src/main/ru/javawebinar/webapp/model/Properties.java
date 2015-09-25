package main.ru.javawebinar.webapp.model;

/**
 * Created by ArturDS on 25.09.2015.
 */
public abstract class Properties  {
    private String name;
   // private String years;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;
}
