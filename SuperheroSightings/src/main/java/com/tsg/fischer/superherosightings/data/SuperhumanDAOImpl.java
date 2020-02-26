package com.tsg.fischer.superherosightings.data;

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
public class SuperhumanDAOImpl implements SuperhumanDAO {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Superhuman addSuperhuman(Superhuman superhuman) {
        jdbc.update("INSERT INTO Superhuman(SuperhumanName,SuperhumanDescription,SuperpowerID) "+
                        "VALUES(?,?,?)",
                superhuman.getName(), superhuman.getDescription(), superhuman.getSuperpower().getId());
        superhuman.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return superhuman;
    }

    @Override
    public Superhuman getSuperhumanById(int id) {
        try {
            Superhuman superhuman = jdbc.queryForObject("SELECT * FROM Superhuman WHERE SuperhumanID = ?",
                    new SuperhumanMapper(), id);
            superhuman.setSuperpower(getSuperpowerForSuperhumanId(id));
            return superhuman;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private Superpower getSuperpowerForSuperhumanId(int id) {
        return jdbc.queryForObject("SELECT Superpower.* FROM Superpower "+
                        "JOIN Superhuman ON Superhuman.SuperpowerID = Superpower.SuperpowerID "+
                        "WHERE SuperhumanID = ?",
                        new SuperpowerDAOImpl.SuperpowerMapper(), id);
    }

    @Override
    public List<Superhuman> getAllSuperhumans() {
        List<Superhuman> superhumans = jdbc.query("SELECT * FROM Superhuman", new SuperhumanMapper());
        associateSuperpowers(superhumans);
        return superhumans;
    }

    private void associateSuperpowers(List<Superhuman> superhumans) {
        for (Superhuman superhuman : superhumans) {
            superhuman.setSuperpower(getSuperpowerForSuperhumanId(superhuman.getId()));
        }
    }

    @Override
    public void updateSuperhuman(Superhuman superhuman) {
        jdbc.update("UPDATE Superhuman SET SuperhumanName = ?, SuperhumanDescription = ?, SuperpowerID = ? "+
                        "WHERE SuperhumanID = ?",
                        superhuman.getName(), superhuman.getDescription(), superhuman.getSuperpower().getId(),
                        superhuman.getId());
    }

    @Override
    @Transactional
    public void deleteSuperhumanById(int id) {
        jdbc.update("DELETE FROM SuperhumanInstitution WHERE SuperhumanID = ?", id);
        jdbc.update("DELETE FROM Sighting WHERE SuperhumanID = ?", id);
        jdbc.update("DELETE FROM Superhuman WHERE SuperhumanID = ?", id);
    }

    public static final class SuperhumanMapper implements RowMapper<Superhuman> {

        @Override
        public Superhuman mapRow(ResultSet rs, int index) throws SQLException {
            Superhuman superhuman = new Superhuman();
            superhuman.setId(rs.getInt("SuperhumanID"));
            superhuman.setName(rs.getString("SuperhumanName"));
            superhuman.setDescription(rs.getString("SuperhumanDescription"));
            return superhuman;
        }
    }
}
