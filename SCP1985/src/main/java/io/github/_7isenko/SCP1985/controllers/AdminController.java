package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.controllers.forms.AdminForm;
import io.github._7isenko.SCP1985.model.GeneratingEntitiesSaver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 7isenko
 */
@Controller
public class AdminController {

    private final String successMessage = "Все данные заполнены!";

    private final GeneratingEntitiesSaver generator;

    public AdminController(GeneratingEntitiesSaver generator) {
        this.generator = generator;
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String showAdminPage(Model model) {
        AdminForm adminForm = new AdminForm();
        model.addAttribute("adminForm", adminForm);

        return "admin";
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.POST)
    public String generate(Model model, @ModelAttribute("adminForm") AdminForm adminForm) {
        model.addAttribute("successMessage", null);

        generator.saveRandomLocations(adminForm.getFoundationAmount());
        generator.saveRandomFoundations(adminForm.getFoundationAmount());

        generator.saveRandomSCPs(adminForm.getScpAmount());

        generator.saveRandomPersonnel(adminForm.getPersonnelAmount());
        generator.saveRandomPersonnelKeys(adminForm.getPersonnelAmount());

        generator.saveRandomItems(adminForm.getItemAmount());
        generator.saveRandomEquipment(adminForm.getEquipmentAmount());
        generator.saveRandomEquipmentContents();

        generator.saveRandomMobileGroups(adminForm.getMobileGroupAmount());
        generator.saveRandomMobileGroupsMembers();

        generator.saveRandomPrimings(adminForm.getPrimingsAmount());
        generator.saveRandomExcursionLogs();

        model.addAttribute("successMessage", successMessage);
        return "admin";
    }

}
