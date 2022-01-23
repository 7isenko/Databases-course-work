package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.object_types.ExcursionLogType;
import io.github._7isenko.SCP1985.model.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author 7isenko
 */
@Controller
public class ReportController {

    // Must be static to be visible
    private static List<ExcursionLogType> reports;
    private final ExcursionLogTypeRepository reportsRepository;


    public ReportController(ExcursionLogTypeRepository reportsRepository) {
        reports = reportsRepository.makeReports();
        this.reportsRepository = reportsRepository;
    }

    @RequestMapping(value = { "/report" }, method = RequestMethod.GET)
    public String index(Model model) {
        reports = reportsRepository.makeReports();
        model.addAttribute("reports", reports);
        return "report";
    }

}
