package com.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.model.Superpower;

import java.util.List;

public interface SuperpowerDAO {
    Superpower addSuperpower(Superpower superpower);
    Superpower getSuperpowerById(int id);
    List<Superpower> getAllSuperpowers();
    void updateSuperpower(Superpower superpower);
    void deleteSuperpowerById(int id);
}
