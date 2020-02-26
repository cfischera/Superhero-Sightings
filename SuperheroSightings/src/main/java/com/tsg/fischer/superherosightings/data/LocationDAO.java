package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Location;

import java.util.List;

public interface LocationDAO {
    Location addLocation(Location location);
    Location getLocationById(int id);
    List<Location> getAllLocations();
    void updateLocation(Location location);
    void deleteLocationById(int id);
}
