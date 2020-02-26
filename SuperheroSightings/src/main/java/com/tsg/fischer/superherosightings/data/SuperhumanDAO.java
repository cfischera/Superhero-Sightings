package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Superhuman;

import java.util.List;

public interface SuperhumanDAO {
    Superhuman addSuperhuman(Superhuman superhuman);
    Superhuman getSuperhumanById(int id);
    List<Superhuman> getAllSuperhumans();
    void updateSuperhuman(Superhuman superhuman);
    void deleteSuperhumanById(int id);
}
