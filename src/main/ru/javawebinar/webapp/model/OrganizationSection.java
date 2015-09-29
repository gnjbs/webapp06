package main.ru.javawebinar.webapp.model;

import java.util.Collection;

/**
 * Created by ArturDS on 29.09.2015.
 */
public class OrganizationSection extends  Section{
    private Collection<Organization> organizations;

    public Collection<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Collection<Organization> organizations) {
        this.organizations = organizations;
    }

    public OrganizationSection(String type, Collection<Organization> organizations) {

        super(type);
        this.organizations = organizations;
    }
}
