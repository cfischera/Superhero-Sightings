package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Institution;

import java.util.List;

public interface InstitutionDAO {
    Institution addInstitution(Institution institution);
    Institution getInstitutionById(int id);
    List<Institution> getAllInstitutions();
    void updateInstitution(Institution institution);
    void deleteInstitutionById(int id);
}
