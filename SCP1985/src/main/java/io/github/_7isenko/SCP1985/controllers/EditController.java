package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.entities.ExcursionLogEntity;
import io.github._7isenko.SCP1985.model.object_types.ExcursionLogType;
import io.github._7isenko.SCP1985.model.repositories.ExcursionLogEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.ExcursionLogTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String update(@ModelAttribute("report") ExcursionLogType report) {
        reportsRepository.updateExcursionLog(report.getId(), report.getReality_description(),
                report.getLog_status().toString(), report.getNote());

        Optional<ExcursionLogEntity> optionalExcursionLog = excursionLogEntityRepository.findById(report.getId());
        if (optionalExcursionLog.isPresent()) {
            reportsRepository.updateTimeBackToFoundation(report.getReturn_to_foundation(),
                    optionalExcursionLog.get().getRetrievalId());
        } else {
            // TODO: Рома, думаю тут нужно сообщение об ошибке вывести.
        }
        return "redirect:report";
    }

    @Transactional
    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public String index(@ModelAttribute("id") int id, Model model) {
        List<ExcursionLogType> reports = reportsRepository.makeReports();
        ExcursionLogType report = reports
                .stream()
                .filter(excursionLogType -> excursionLogType.getId() == id)
                .findFirst().orElse(new ExcursionLogType());
        model.addAttribute("report", report);
        return "edit";
    }

}
