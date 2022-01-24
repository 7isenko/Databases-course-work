package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.controllers.forms.PrimingForm;
import io.github._7isenko.SCP1985.model.entities.EquipmentEntity;
import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.model.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.model.repositories.*;
import io.github._7isenko.SCP1985.model.utils.PersonnelHelper;
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

    private final ScpObjectEntityRepository scpObjectEntityRepository;
    private static List<PersonnelEntity> personnelEntities;
    private final PersonnelEntityRepository personnelEntityRepository;
    private static List<EquipmentEntity> equipmentEntities;
    private final EquipmentEntityRepository equipmentEntityRepository;
    private final PrimingEntityRepository primingEntityRepository;
    private final ExcursionLogEntityRepository excursionLogEntityRepository;

    public MainController(ScpObjectEntityRepository scpObjectEntityRepository,
                          PersonnelEntityRepository personnelEntityRepository,
                          EquipmentEntityRepository equipmentEntityRepository,
                          PrimingEntityRepository primingEntityRepository,
                          ExcursionLogEntityRepository excursionLogEntityRepository) {
        this.scpObjectEntityRepository = scpObjectEntityRepository;
        this.personnelEntityRepository = personnelEntityRepository;
        this.equipmentEntityRepository = equipmentEntityRepository;
        this.primingEntityRepository = primingEntityRepository;
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

        for (PersonnelEntity personnel : personnelEntities) {
            if (personnel.getName().equals(personnel_name) &&
                    personnel.getClearanceLevel().toString().equals(level)) {
                personnel_id = personnel.getId();
                break;
            }
        }
        for (EquipmentEntity equipment : equipmentEntities) {
            if (equipment.getName().equals(equipment_name)) {
                equipment_id = equipment.getId();
                break;
            }
        }

        primingEntityRepository.executePriming(scp_id, personnel_id);
        excursionLogEntityRepository.goOnExcursion(scp_id, personnel_id, equipment_id);

        return "main";
    }

    @RequestMapping(value = {"/main" }, method = RequestMethod.GET)
    public String index(Model model) {
        List<ScpObjectEntity> scpEntities = scpObjectEntityRepository.findAllOrderById();
        model.addAttribute("scpEntities", scpEntities);
        personnelEntities = PersonnelHelper.getAllowedPersonnel(personnelEntityRepository.findAll());
        model.addAttribute("personnelEntities", personnelEntities);
        equipmentEntities = equipmentEntityRepository.findAll();
        model.addAttribute("equipmentEntities", equipmentEntities);
        PrimingForm primingForm = new PrimingForm();
        model.addAttribute("priming", primingForm);
        return "main";
    }

}
