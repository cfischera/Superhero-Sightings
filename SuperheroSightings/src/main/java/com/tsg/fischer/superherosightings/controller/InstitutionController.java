package com.tsg.fischer.superherosightings.controller;

import com.tsg.fischer.superherosightings.data.InstitutionDAO;
import com.tsg.fischer.superherosightings.data.LocationDAO;
import com.tsg.fischer.superherosightings.data.SuperhumanDAO;
import com.tsg.fischer.superherosightings.model.Institution;
import com.tsg.fischer.superherosightings.model.Location;
import com.tsg.fischer.superherosightings.model.Superhuman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InstitutionController {
    @Autowired
    InstitutionDAO institutionDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    SuperhumanDAO superhumanDAO;

    @GetMapping("institutions")
    public String displayInstitutions(Model model) {
        List<Institution> institutions = institutionDAO.getAllInstitutions();
        List<Location> locations = locationDAO.getAllLocations();
        List<Superhuman> superhumans = superhumanDAO.getAllSuperhumans();
        model.addAttribute("institutions", institutions);
        model.addAttribute("locations", locations);
        model.addAttribute("superhumans", superhumans);
        return "institutions";
    }

    @PostMapping("addInstitution")
    public String addInstitution(Institution institution, HttpServletRequest request) {
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String[] superhumanIds = request.getParameterValues("superhumanId");

        institution.setLocation(locationDAO.getLocationById(locationId));

        List<Superhuman> superhumans = new ArrayList<>();
        for(String superhumanId : superhumanIds) {
            superhumans.add(superhumanDAO.getSuperhumanById(Integer.parseInt(superhumanId)));
        }
        institution.setSuperhumans(superhumans);
        institutionDAO.addInstitution(institution);

        return "redirect:/institutions";
    }

    @GetMapping("institutionDetail")
    public String institutionDetail(Integer id, Model model) {
        Institution institution = institutionDAO.getInstitutionById(id);
        model.addAttribute("institution", institution);
        return "institutionDetail";
    }

    @GetMapping("deleteInstitution")
    public String deleteInstitution(Integer id) {
        institutionDAO.deleteInstitutionById(id);
        return "redirect:/institutions";
    }

    @GetMapping("editInstitution")
    public String editInstitution(Integer id, Model model) {
        Institution institution = institutionDAO.getInstitutionById(id);
        List<Location> locations = locationDAO.getAllLocations();
        List<Superhuman> superhumans = superhumanDAO.getAllSuperhumans();
        model.addAttribute("institution", institution);
        model.addAttribute("locations", locations);
        model.addAttribute("superhumans", superhumans);
        return "editInstitution";
    }

    @PostMapping("editInstitution")
    public String performEditInstitution(Institution institution, HttpServletRequest request) {
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String[] superhumanIds = request.getParameterValues("superhumanId");

        institution.setLocation(locationDAO.getLocationById(locationId));

        List<Superhuman> superhumans = new ArrayList<>();
        for(String superhumanId : superhumanIds) {
            superhumans.add(superhumanDAO.getSuperhumanById(Integer.parseInt(superhumanId)));
        }
        institution.setSuperhumans(superhumans);
        institution.setName(request.getParameter("name"));
        institution.setDescription(request.getParameter("description"));
        institution.setContactInfo(request.getParameter("contactInfo"));
        institutionDAO.updateInstitution(institution);

        return "redirect:/institutions";
    }
}
