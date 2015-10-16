package main.ru.javawebinar.webapp.model;

import java.util.Arrays;
import java.util.List;

/**
 * GKislin
 * 26.12.2014.
 */
public class OrganizationSection extends Section {
    private final List<Organization> organizations;

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizationSection that = (OrganizationSection) o;

        return !(organizations != null ? !organizations.equals(that.organizations) : that.organizations != null);

    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

}
