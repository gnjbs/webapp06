package main.ru.javawebinar.webapp.model;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class Section {
    private SectionType sectionType;
    private String description;

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Section(SectionType sectionType, String description) {

        this.sectionType = sectionType;
        this.description = description;
    }



}
