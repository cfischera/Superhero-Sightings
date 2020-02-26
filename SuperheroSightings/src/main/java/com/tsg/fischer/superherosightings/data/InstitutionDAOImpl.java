package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Institution;
import com.tsg.fischer.superherosightings.model.Location;
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
public class InstitutionDAOImpl implements InstitutionDAO {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Institution addInstitution(Institution institution) {
        jdbc.update("INSERT INTO Institution(InstitutionName,InstitutionDescription,ContactInformation,"+
                        "LocationID) VALUES(?,?,?,?)",
                        institution.getName(), institution.getDescription(), institution.getContactInfo(),
                        institution.getLocation().getId());
        institution.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        insertSuperhumanInstitution(institution);
        return institution;
    }

    private void insertSuperhumanInstitution(Institution institution) {
        for(Superhuman superhuman : institution.getSuperhumans()) {
            jdbc.update("INSERT INTO SuperhumanInstitution(SuperhumanID,InstitutionID) VALUES(?,?)",
                            superhuman.getId(),
                            institution.getId());
        }
    }


    @Override
    public Institution getInstitutionById(int id) {
        try {
            Institution institution = jdbc.queryForObject("SELECT * FROM Institution WHERE InstitutionID = ?",
                    new InstitutionMapper(), id);
            institution.setLocation(getLocationForInstitutionId(institution.getId()));
            institution.setSuperhumans(getSuperhumansForInstitutionId(institution.getId()));
            return institution;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private Location getLocationForInstitutionId(int id) {
        return jdbc.queryForObject("SELECT Location.* FROM Location "+
                        "JOIN Institution ON Institution.LocationID = Location.LocationID "+
                        "WHERE InstitutionID = ?",
                new LocationDAOImpl.LocationMapper(), id);
    }

    private List<Superhuman> getSuperhumansForInstitutionId(int id) {
        List<Superhuman> superhumans = jdbc.query("SELECT Superhuman.* FROM Superhuman "+
                "JOIN SuperhumanInstitution ON SuperhumanInstitution.SuperhumanID = "+
                "Superhuman.SuperhumanID WHERE SuperhumanInstitution.InstitutionID = ?",
                new SuperhumanDAOImpl.SuperhumanMapper(), id);
        for(Superhuman superhuman : superhumans) {
            superhuman.setSuperpower(getSuperpowerForSuperhumanId(superhuman.getId()));
        }
        return superhumans;
    }

    private Superpower getSuperpowerForSuperhumanId(int id) {
        return jdbc.queryForObject("SELECT Superpower.* FROM Superpower "+
                        "JOIN Superhuman ON Superhuman.SuperpowerID = Superpower.SuperpowerID "+
                        "WHERE SuperhumanID = ?",
                new SuperpowerDAOImpl.SuperpowerMapper(), id);
    }

    @Override
    public List<Institution> getAllInstitutions() {
        List<Institution> institutions = jdbc.query("SELECT * FROM Institution", new InstitutionMapper());
        associateLocationsAndSuperhumans(institutions);
        return institutions;
    }

    private void associateLocationsAndSuperhumans(List<Institution> institutions) {
        for(Institution institution : institutions) {
            institution.setLocation(getLocationForInstitutionId(institution.getId()));
            institution.setSuperhumans(getSuperhumansForInstitutionId(institution.getId()));
        }
    }

    @Override
    public void updateInstitution(Institution institution) {
        jdbc.update("UPDATE Institution SET InstitutionName = ?, InstitutionDescription = ?, "+
                        "ContactInformation = ?, LocationID = ? WHERE InstitutionID = ?",
                        institution.getName(), institution.getDescription(), institution.getContactInfo(),
                        institution.getLocation().getId(), institution.getId());
        institution.setSuperhumans(getSuperhumansForInstitutionId(institution.getId()));
    }

    @Override
    @Transactional
    public void deleteInstitutionById(int id) {
        jdbc.update("DELETE FROM SuperhumanInstitution WHERE InstitutionID = ?", id);
        jdbc.update("DELETE FROM Institution WHERE InstitutionID = ?", id);
    }

    public static final class InstitutionMapper implements RowMapper<Institution> {

        @Override
        public Institution mapRow(ResultSet rs, int index) throws SQLException {
            Institution institution = new Institution();
            institution.setId(rs.getInt("InstitutionID"));
            institution.setName(rs.getString("InstitutionName"));
            institution.setDescription(rs.getString("InstitutionDescription"));
            institution.setContactInfo(rs.getString("ContactInformation"));
            return institution;
        }
    }
}
