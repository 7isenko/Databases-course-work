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

/**..
 * @author 7isenko
 */
@Controller
public class EditController {

    private final ExcursionLogTypeRepository reportsRepository;
    private List<ExcursionLogEntity> excursionLogEntities;
    private final ExcursionLogEntityRepository excursionLogEntityRepository;


    public EditController(ExcursionLogTypeRepository reportsRepository,
                          ExcursionLogEntityRepository excursionLogEntityRepository) {
        this.reportsRepository = reportsRepository;
        this.excursionLogEntityRepository = excursionLogEntityRepository;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    public String update(@ModelAttribute("report") ExcursionLogType report){
        reportsRepository.updateExcursionLog(report.getId(), report.getReality_description(),
                                                report.getLog_status().toString(), report.getNote());
        excursionLogEntities = excursionLogEntityRepository.findAll();
        int retrieval_id = 0;
        for (ExcursionLogEntity excursionLog : excursionLogEntities){
            if (excursionLog.getId() == report.getId()){
                retrieval_id = excursionLog.getRetrievalId();
            }
        }
        reportsRepository.updateTimeBackToFoundation(report.getReturn_to_foundation(), retrieval_id);
        return "redirect:report";
    }

    @Transactional
    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public String index(@ModelAttribute("id") int id, Model model) {
        List<ExcursionLogType> reports = reportsRepository.makeReports();
        ExcursionLogType report = new ExcursionLogType();
        for (int i = 0; i < reports.size(); ++i){
            if (reports.get(i).getId() == id){
                report = reports.get(i);
                break;
            }
        }
        model.addAttribute("report", report);
        return "edit";
    }

}
