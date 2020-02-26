package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Sighting;

import java.util.List;

public interface SightingDAO {
    Sighting addSighting(Sighting sighting);
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
}
