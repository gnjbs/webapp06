package main.ru.javawebinar.webapp.model;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class TextSection extends Section {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextSection(String type, String content) {

        super(type);
        this.content = content;
    }
}
