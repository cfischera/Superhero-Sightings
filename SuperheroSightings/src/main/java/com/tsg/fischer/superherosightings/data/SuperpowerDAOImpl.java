package com.tsg.fischer.superherosightings.data;

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
public class SuperpowerDAOImpl implements SuperpowerDAO {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        jdbc.update("INSERT INTO Superpower(SuperpowerName) VALUES(?)",
                superpower.getName());
        superpower.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return superpower;
    }

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            return jdbc.queryForObject("SELECT * FROM Superpower WHERE SuperpowerID = ?",
                                            new SuperpowerMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        return jdbc.query("SELECT * FROM Superpower", new SuperpowerMapper());
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        jdbc.update("UPDATE Superpower SET SuperpowerName = ? WHERE SuperpowerID = ?",
                        superpower.getName(), superpower.getId());
    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int id) {
        jdbc.update("DELETE SuperhumanInstitution.* FROM SuperhumanInstitution JOIN Superhuman "+
                        "ON SuperhumanInstitution.SuperhumanID = Superhuman.SuperhumanID "+
                        "WHERE Superhuman.SuperpowerID = ?", id);
        jdbc.update("DELETE Sighting.* FROM Sighting JOIN Superhuman ON Sighting.SuperhumanID = Superhuman.SuperhumanID "+
                        "WHERE Superhuman.SuperpowerID = ?", id);
        jdbc.update("DELETE FROM Superhuman WHERE SuperpowerID = ?", id);
        jdbc.update("DELETE FROM Superpower WHERE SuperpowerID = ?", id);
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("SuperpowerID"));
            superpower.setName(rs.getString("SuperpowerName"));
            return superpower;
        }
    }
}
