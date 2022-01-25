package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.object_types.ExcursionLogType;
import io.github._7isenko.SCP1985.model.repositories.ExcursionLogTypeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
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

    @RequestMapping(value = {"/report"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute("rep") ExcursionLogType report, RedirectAttributes rattrs){
        int id = report.getId();
        rattrs.addAttribute("id", id);
        return "redirect:edit";
    }

    @Transactional
    @RequestMapping(value = {"/report"}, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        List<ExcursionLogType> reports = reportsRepository.makeReports();
        reports.sort((o1, o2) -> {
            if (o1.getId() == o2.getId()) return 0;
            return o1.getId() < o2.getId() ? -1 : 1;
        });
        model.addAttribute("reports", reports);
        ExcursionLogType report = new ExcursionLogType();
        model.addAttribute("rep", report);
        model.addAttribute("authorities", authentication.getAuthorities().toString());
        return "report";
    }

}
