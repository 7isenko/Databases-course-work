package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.model.repositories.ScpObjectEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author 7isenko
 */
@Controller
public class MainController {

    // Must be static to be visible
    private static List<ScpObjectEntity> scpEntities;


    public MainController(ScpObjectEntityRepository scpObjectEntityRepository) {
        scpEntities = scpObjectEntityRepository.findAll();
    }

    @RequestMapping(value = { "/", "/main" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("scpEntities", scpEntities);
        return "main";
    }

}
