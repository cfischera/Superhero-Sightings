package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Location;
import com.tsg.fischer.superherosightings.model.Sighting;
import com.tsg.fischer.superherosightings.model.Superhuman;
import com.tsg.fischer.superherosightings.model.Superpower;
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
public class SightingDAOImpl implements SightingDAO {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        jdbc.update("INSERT INTO Sighting(SightingDate,LocationID,SuperhumanID) "+
                        "VALUES(?,?,?)",
                sighting.getDate(), sighting.getLocation().getId(), sighting.getSuperhuman().getId());
        sighting.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return sighting;
    }

    @Override
    public Sighting getSightingById(int id) {
        try {
            Sighting sighting = jdbc.queryForObject("SELECT * FROM Sighting WHERE SightingID = ?",
                    new SightingMapper(), id);
            sighting.setLocation(getLocationForSightingId(sighting.getId()));
            sighting.setSuperhuman(getSuperhumanForSightingId(sighting.getId()));
            return sighting;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private Location getLocationForSightingId(int id) {
        return jdbc.queryForObject("SELECT Location.* FROM Location "+
                        "JOIN Sighting ON Sighting.LocationID = Location.LocationID "+
                        "WHERE SightingID = ?",
                new LocationDAOImpl.LocationMapper(), id);
    }

    private Superhuman getSuperhumanForSightingId(int id) {
        Superhuman superhuman = jdbc.queryForObject("SELECT Superhuman.* FROM Superhuman "+
                        "JOIN Sighting ON Sighting.SuperhumanID = Superhuman.SuperhumanID "+
                        "WHERE SightingID = ?",
                new SuperhumanDAOImpl.SuperhumanMapper(), id);
        superhuman.setSuperpower(getSuperpowerForSuperhumanId(superhuman.getId()));
        return superhuman;
    }

    private Superpower getSuperpowerForSuperhumanId(int id) {
        return jdbc.queryForObject("SELECT Superpower.* FROM Superpower "+
                        "JOIN Superhuman ON Superhuman.SuperpowerID = Superpower.SuperpowerID "+
                        "WHERE SuperhumanID = ?",
                new SuperpowerDAOImpl.SuperpowerMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = jdbc.query("SELECT * FROM Sighting", new SightingMapper());
        associateLocationsAndSuperhumans(sightings);
        return sightings;
    }

    private void associateLocationsAndSuperhumans(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSightingId(sighting.getId()));
            sighting.setSuperhuman(getSuperhumanForSightingId(sighting.getId()));
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
        jdbc.update("UPDATE Sighting SET SightingDate = ?, LocationID = ?, SuperhumanID = ? "+
                        "WHERE SightingID = ?",
                sighting.getDate(), sighting.getLocation().getId(), sighting.getSuperhuman().getId(),
                sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        jdbc.update("DELETE FROM Sighting WHERE SightingID = ?", id);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("SightingID"));
            sighting.setDate(rs.getTimestamp("SightingDate").toLocalDateTime());
            return sighting;
        }
    }
}
