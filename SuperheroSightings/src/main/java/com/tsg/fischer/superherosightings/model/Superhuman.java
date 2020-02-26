package com.tsg.fischer.superherosightings.model;

import java.util.Objects;

public class Superhuman {
    private int id;
    private String name, description;
    private Superpower superpower;

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

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superhuman that = (Superhuman) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(superpower, that.superpower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, superpower);
    }
}
