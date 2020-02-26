package com.tsg.fischer.superherosightings.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Institution {
    private int id;
    private String name, description, contactInfo;
    private Location location;
    private List<Superhuman> superhumans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Superhuman> getSuperhumans() {
        return superhumans;
    }

    public void setSuperhumans(List<Superhuman> superhumans) {
        this.superhumans = superhumans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution that = (Institution) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(contactInfo, that.contactInfo) &&
                Objects.equals(location, that.location) &&
                Objects.equals(superhumans, that.superhumans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, contactInfo, location, superhumans);
    }
}
