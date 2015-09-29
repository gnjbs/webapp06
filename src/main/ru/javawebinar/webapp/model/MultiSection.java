package main.ru.javawebinar.webapp.model;

import java.util.ArrayList;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class MultiSection extends Section {

    private ArrayList<String> contents;

    public ArrayList<String> getContents() {
        return contents;
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    public MultiSection(String type, ArrayList<String> contents) {

        super(type);
        this.contents = contents;
    }
}
