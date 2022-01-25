package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.entities.ExcursionLogEntity;
import io.github._7isenko.SCP1985.model.object_types.ExcursionLogType;
import io.github._7isenko.SCP1985.model.repositories.ExcursionLogEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.ExcursionLogTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * ..
 *
 * @author 7isenko
 */
@Controller
public class EditController {

    private final ExcursionLogTypeRepository reportsRepository;
    private final ExcursionLogEntityRepository excursionLogEntityRepository;

    public EditController(ExcursionLogTypeRepository reportsRepository, ExcursionLogEntityRepository excursionLogEntityRepository) {
        this.reportsRepository = reportsRepository;
        this.excursionLogEntityRepository = excursionLogEntityRepository;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    public String update(@ModelAttribute("report") ExcursionLogType report, BindingResult result, RedirectAttributes rattrs) {
        reportsRepository.updateExcursionLog(report.getId(), report.getReality_description(),
                report.getLog_status().toString(), report.getNote());

        int id = report.getId();
        rattrs.addAttribute("id", id);

        Optional<ExcursionLogEntity> optionalExcursionLog = excursionLogEntityRepository.findById(report.getId());
        if (optionalExcursionLog.isPresent()) {
            if (report.getReturn_to_foundation() == null ||
                    report.getReturn_to_reality().compareTo(report.getReturn_to_foundation()) > 0){
                return "redirect:edit?error";
            }
            reportsRepository.updateTimeBackToFoundation(report.getReturn_to_foundation(),
                    optionalExcursionLog.get().getRetrievalId());
        } else {
            return "redirect:edit?error";
        }
        return "redirect:report";
    }

    @Transactional
    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public String index(@ModelAttribute("id") int id, Model model) {
        addIndexForm(model, id);
        return "edit";
    }

    @Transactional
    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET, params = "error")
    public String indexWithError(@ModelAttribute("id") int id, Model model, @RequestParam("error") String err) {
        addIndexForm(model,id);
        model.addAttribute("error", "Неправильное заполнение. Поля не могут быть пустыми");
        return "edit";
    }

    private void addIndexForm(Model model, int id){
        List<ExcursionLogType> reports = reportsRepository.makeReports();
        ExcursionLogType report = reports
                .stream()
                .filter(excursionLogType -> excursionLogType.getId() == id)
                .findFirst().orElse(new ExcursionLogType());
        model.addAttribute("report", report);
    }


}
