package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDAOImpl implements LocationDAO {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Location addLocation(Location location) {
        jdbc.update("INSERT INTO Location(LocationName,LocationDescription,LocationAddress,Latitude,Longitude) "+
                        "VALUES(?,?,?,?,?)",
                location.getName(), location.getDescription(), location.getAddress(),
                location.getLatitude(), location.getLongitude());
        location.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return location;
    }

    @Override
    public Location getLocationById(int id) {
        try {
            return jdbc.queryForObject("SELECT * FROM Location WHERE LocationID = ?",
                    new LocationMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbc.query("SELECT * FROM Location", new LocationMapper());
    }

    @Override
    public void updateLocation(Location location) {
        jdbc.update("UPDATE Location SET LocationName = ?, LocationDescription = ?, LocationAddress = ?, "+
                        "Latitude = ?, Longitude = ? WHERE LocationID = ?",
                        location.getName(), location.getDescription(), location.getAddress(),
                        location.getLatitude(), location.getLongitude(), location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        jdbc.update("DELETE FROM Sighting WHERE LocationID = ?", id);
        jdbc.update("DELETE FROM Location WHERE LocationID = ?", id);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("LocationID"));
            location.setName(rs.getString("LocationName"));
            location.setDescription(rs.getString("LocationDescription"));
            location.setAddress(rs.getString("LocationAddress"));
            location.setLatitude(rs.getDouble("Latitude"));
            location.setLongitude(rs.getDouble("Longitude"));
            return location;
        }
    }
}
