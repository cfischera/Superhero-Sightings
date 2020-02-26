package com.tsg.fischer.superherosightings.controller;

import com.tsg.fischer.superherosightings.data.SuperpowerDAO;
import com.tsg.fischer.superherosightings.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SuperpowerController {
    @Autowired
    SuperpowerDAO superpowerDAO;

    @GetMapping("superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDAO.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }

    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request) {
        String name = request.getParameter("name");

        Superpower superpower = new Superpower();
        superpower.setName(name);

        superpowerDAO.addSuperpower(superpower);

        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerDAO.deleteSuperpowerById(id);

        return "redirect:/superpowers";
    }

    @GetMapping("editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDAO.getSuperpowerById(id);

        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }

    @PostMapping("editSuperpower")
    public String performEditSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDAO.getSuperpowerById(id);

        superpower.setName(request.getParameter("name"));

        superpowerDAO.updateSuperpower(superpower);

        return "redirect:/superpowers";
    }
}
