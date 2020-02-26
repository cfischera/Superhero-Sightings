package com.tsg.fischer.superherosightings.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Sighting {
    private int id;
    private LocalDateTime date;
    private Location location;
    private Superhuman superhuman;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Superhuman getSuperhuman() {
        return superhuman;
    }

    public void setSuperhuman(Superhuman superhuman) {
        this.superhuman = superhuman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return id == sighting.id &&
                Objects.equals(date, sighting.date) &&
                Objects.equals(location, sighting.location) &&
                Objects.equals(superhuman, sighting.superhuman);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, location, superhuman);
    }
}
