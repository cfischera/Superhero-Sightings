package com.tsg.fischer.superherosightings.controller;

import com.tsg.fischer.superherosightings.data.LocationDAO;
import com.tsg.fischer.superherosightings.data.SightingDAO;
import com.tsg.fischer.superherosightings.data.SuperhumanDAO;
import com.tsg.fischer.superherosightings.model.Location;
import com.tsg.fischer.superherosightings.model.Sighting;
import com.tsg.fischer.superherosightings.model.Superhuman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class SightingController {
    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    SuperhumanDAO superhumanDAO;

    @Autowired
    LocationDAO locationDAO;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDAO.getAllSightings();
        List<Superhuman> superhumans = superhumanDAO.getAllSuperhumans();
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superhumans", superhumans);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        int superhumanId = Integer.parseInt(request.getParameter("superhumanId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        sighting.setSuperhuman(superhumanDAO.getSuperhumanById(superhumanId));
        sighting.setLocation(locationDAO.getLocationById(locationId));

        sightingDAO.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDAO.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDAO.getSightingById(id);
        List<Superhuman> superhumans = superhumanDAO.getAllSuperhumans();
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("superhumans", superhumans);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDAO.getSightingById(id);

        sighting.setSuperhuman(superhumanDAO.getSuperhumanById(Integer.parseInt(request.getParameter("superhumanId"))));
        sighting.setLocation(locationDAO.getLocationById(Integer.parseInt(request.getParameter("locationId"))));

        sightingDAO.updateSighting(sighting);

        return "redirect:/sightings";
    }
}
