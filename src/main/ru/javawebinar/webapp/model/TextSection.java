package main.ru.javawebinar.webapp.model;

/**
 * GKislin
 * 02.10.2015.
 */
public class TextSection extends Section{
    private final String content;

    public TextSection(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextSection that = (TextSection) o;

        return !(content != null ? !content.equals(that.content) : that.content != null);

    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}
