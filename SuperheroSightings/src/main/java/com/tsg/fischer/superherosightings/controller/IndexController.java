package com.tsg.fischer.superherosightings.controller;

import com.tsg.fischer.superherosightings.data.SightingDAO;
import com.tsg.fischer.superherosightings.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    SightingDAO sightingDAO;

    @GetMapping("/")
    public String index(Model model) {
        List<Sighting> sightings = sightingDAO.getAllSightings();
        Collections.sort(sightings, new SortByDate());
        List<Sighting> sightingsRecent = new ArrayList<>();
        int counter = 0;
        while(counter < 10 && counter < sightings.size()) {
            sightingsRecent.add(sightings.get(counter));
            counter++;
        }
        model.addAttribute("sightings", sightingsRecent);
        return "index";
    }

    private class SortByDate implements Comparator<Sighting> {

        @Override
        public int compare(Sighting s1, Sighting s2) {
            if(s1.getDate().isBefore(s2.getDate())) {
                return 1;
            } else if(s1.getDate().isAfter(s2.getDate())) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
