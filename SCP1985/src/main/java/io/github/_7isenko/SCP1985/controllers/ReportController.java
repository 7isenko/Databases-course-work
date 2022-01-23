package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.object_types.ExcursionLogType;
import io.github._7isenko.SCP1985.model.repositories.ExcursionLogTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author 7isenko
 */
@Controller
public class ReportController {

    private final ExcursionLogTypeRepository reportsRepository;


    public ReportController(ExcursionLogTypeRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    @Transactional
    @RequestMapping(value = {"/report"}, method = RequestMethod.GET)
    public String index(Model model) {
        List<ExcursionLogType> reports = reportsRepository.makeReports();
        model.addAttribute("reports", reports);
        return "report";
    }

}
