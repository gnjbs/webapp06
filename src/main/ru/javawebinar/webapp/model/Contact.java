package main.ru.javawebinar.webapp.model;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class Contact {

    private ContactTypes type;
    private String description;

    public Contact(ContactTypes type, String description) {
        this.type = type;
        this.description = description;
    }

    public ContactTypes getType() {

        return type;
    }

    public void setType(ContactTypes type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
