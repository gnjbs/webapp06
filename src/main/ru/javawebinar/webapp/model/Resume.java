package main.ru.javawebinar.webapp.model;

import java.util.Collection;
import java.util.Map;

/**
 * GKislin
 * 18.09.2015.
 */
public class Resume {
    private int id;
    private String fullName;
    private Map<ContactType, String> contacts;
    private String objective;
    private Collection<Properties> properties;

}
