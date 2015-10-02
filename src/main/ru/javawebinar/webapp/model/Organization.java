package main.ru.javawebinar.webapp.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class Organization {
    private final Link homePage;
    private final Collection<Postion> postions;

    public Organization(Link homePage, ArrayList<Postion> postions) {
        this.homePage = homePage;
        this.postions = postions;
    }
}
