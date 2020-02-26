package com.tsg.fischer.superherosightings.controller;

import com.tsg.fischer.superherosightings.data.SuperhumanDAO;
import com.tsg.fischer.superherosightings.data.SuperpowerDAO;
import com.tsg.fischer.superherosightings.model.Superhuman;
import com.tsg.fischer.superherosightings.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SuperhumanController {
    @Autowired
    SuperhumanDAO superhumanDAO;

    @Autowired
    SuperpowerDAO superpowerDAO;

    @GetMapping("superhumans")
    public String displaySuperhumans(Model model) {
        List<Superhuman> superhumans = superhumanDAO.getAllSuperhumans();
        List<Superpower> superpowers = superpowerDAO.getAllSuperpowers();
        model.addAttribute("superhumans", superhumans);
        model.addAttribute("superpowers", superpowers);
        return "superhumans";
    }

    @PostMapping("addSuperhuman")
    public String addSuperhuman(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int superpowerId = Integer.parseInt(request.getParameter("superpowerId"));

        Superhuman superhuman = new Superhuman();
        superhuman.setName(name);
        superhuman.setDescription(description);
        superhuman.setSuperpower(superpowerDAO.getSuperpowerById(superpowerId));

        superhumanDAO.addSuperhuman(superhuman);

        return "redirect:/superhumans";
    }

    @GetMapping("deleteSuperhuman")
    public String deleteSuperhuman(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superhumanDAO.deleteSuperhumanById(id);

        return "redirect:/superhumans";
    }

    @GetMapping("editSuperhuman")
    public String editSuperhuman(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhuman superhuman = superhumanDAO.getSuperhumanById(id);
        List<Superpower> superpowers = superpowerDAO.getAllSuperpowers();
        model.addAttribute("superhuman", superhuman);
        model.addAttribute("superpowers", superpowers);
        return "editSuperhuman";
    }

    @PostMapping("editSuperhuman")
    public String performEditSuperhuman(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhuman superhuman = superhumanDAO.getSuperhumanById(id);

        superhuman.setName(request.getParameter("name"));
        superhuman.setDescription(request.getParameter("description"));
        superhuman.setSuperpower(superpowerDAO.getSuperpowerById(Integer.parseInt(request.getParameter("superpowerId"))));

        superhumanDAO.updateSuperhuman(superhuman);

        return "redirect:/superhumans";
    }
}
