package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.controllers.forms.PrimingForm;
import io.github._7isenko.SCP1985.model.RepositoriesFacade;
import io.github._7isenko.SCP1985.model.entities.EquipmentEntity;
import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.model.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.model.repositories.EquipmentEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.ExcursionLogEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.PersonnelEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.ScpObjectEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final ScpObjectEntityRepository scpObjectEntityRepository;
    private static List<PersonnelEntity> personnelEntities;
    private final PersonnelEntityRepository personnelEntityRepository;
    private static List<EquipmentEntity> equipmentEntities;
    private final EquipmentEntityRepository equipmentEntityRepository;
    private final ExcursionLogEntityRepository excursionLogEntityRepository;

    public MainController(ScpObjectEntityRepository scpObjectEntityRepository,
                          PersonnelEntityRepository personnelEntityRepository,
                          EquipmentEntityRepository equipmentEntityRepository,
                          ExcursionLogEntityRepository excursionLogEntityRepository) {
        scpEntities = scpObjectEntityRepository.findAllOrderById();
        this.scpObjectEntityRepository = scpObjectEntityRepository;
        personnelEntities = personnelEntityRepository.findAll();
        this.personnelEntityRepository = personnelEntityRepository;
        equipmentEntities = equipmentEntityRepository.findAll();
        this.equipmentEntityRepository = equipmentEntityRepository;
        this.excursionLogEntityRepository = excursionLogEntityRepository;
    }

    @RequestMapping(value = {"/main" }, method = RequestMethod.POST)
    public String save(@ModelAttribute("priming") PrimingForm priming){
        int scp_id = Integer.parseInt(priming.getScp_object().substring(4));
        int personnel_id = 0;
        String personnel_name = priming.getPersonnel().split(" ")[1];
        String level = priming.getPersonnel().split(" ")[0].substring(2);
        int equipment_id = 0;
        String equipment_name = priming.getEquipment();
        for (int i = 0; i < personnelEntities.size(); ++i){
            PersonnelEntity personnel = personnelEntities.get(i);
            if (personnel.getName().equals(personnel_name) &&
                    personnel.getClearanceLevel().toString().equals(level)){
                personnel_id = personnel.getId();
                break;
            }
        }
        for (int i = 0; i < equipmentEntities.size(); ++i){
            EquipmentEntity equipment = equipmentEntities.get(i);
            if (equipment.getName().equals(equipment_name)){
                equipment_id = equipment.getId();
                break;
            }
        }
        excursionLogEntityRepository.goOnExcursion(scp_id, personnel_id, equipment_id);
        excursionLogEntityRepository.flush();
        return "main";
    }

    @RequestMapping(value = {"/main" }, method = RequestMethod.GET)
    public String index(Model model) {
        scpEntities = scpObjectEntityRepository.findAllOrderById();
        model.addAttribute("scpEntities", scpEntities);
        personnelEntities = personnelEntityRepository.findAll();
        model.addAttribute("personnelEntities", personnelEntities);
        equipmentEntities = equipmentEntityRepository.findAll();
        model.addAttribute("equipmentEntities", equipmentEntities);
        PrimingForm primingForm = new PrimingForm();
        model.addAttribute("priming", primingForm);
        return "main";
    }

}
