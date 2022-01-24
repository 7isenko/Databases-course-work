package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.entities.LocationEntity;
import io.github._7isenko.SCP1985.model.entities.RetrievalEntity;
import io.github._7isenko.SCP1985.model.repositories.RetrievalEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 7isenko
 */
@Controller
public class MapController {
    private final RetrievalEntityRepository retrievalEntityRepository;


    public MapController(RetrievalEntityRepository retrievalEntityRepository) {
        this.retrievalEntityRepository = retrievalEntityRepository;
    }

    @RequestMapping(value = {"/map"}, method = RequestMethod.GET)
    public String index(Model model) {

        RetrievalEntity retrieval = retrievalEntityRepository.findLast();
        LocationEntity location = retrieval.getLocationByLocationId();

        model.addAttribute("retrieval", retrieval.getSucceed() ? "RETRIEVED" : "RESQUE HER!");
        model.addAttribute("location", location);
        return "map";
    }
}
