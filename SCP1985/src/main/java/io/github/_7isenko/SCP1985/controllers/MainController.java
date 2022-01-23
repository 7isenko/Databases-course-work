package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.model.entities.EquipmentEntity;
import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.model.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.model.repositories.EquipmentEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.PersonnelEntityRepository;
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
    private final ScpObjectEntityRepository scpObjectEntityRepository;
    private static List<PersonnelEntity> personnelEntities;
    private final PersonnelEntityRepository personnelEntityRepository;
    private static List<EquipmentEntity> equipmentEntities;
    private final EquipmentEntityRepository equipmentEntityRepository;


    public MainController(ScpObjectEntityRepository scpObjectEntityRepository, PersonnelEntityRepository personnelEntityRepository, EquipmentEntityRepository equipmentEntityRepository) {
        scpEntities = scpObjectEntityRepository.findAllOrderById();
        this.scpObjectEntityRepository = scpObjectEntityRepository;
        personnelEntities = personnelEntityRepository.findAll();
        this.personnelEntityRepository = personnelEntityRepository;
        equipmentEntities = equipmentEntityRepository.findAll();
        this.equipmentEntityRepository = equipmentEntityRepository;
    }

    @RequestMapping(value = { "/", "/main" }, method = RequestMethod.GET)
    public String index(Model model) {
        scpEntities = scpObjectEntityRepository.findAllOrderById();
        model.addAttribute("scpEntities", scpEntities);
        personnelEntities = personnelEntityRepository.findAll();
        model.addAttribute("personnelEntities", personnelEntities);
        equipmentEntities = equipmentEntityRepository.findAll();
        model.addAttribute("equipmentEntities", equipmentEntities);
        return "main";
    }

}
