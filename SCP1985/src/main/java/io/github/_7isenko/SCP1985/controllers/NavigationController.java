package io.github._7isenko.SCP1985.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 7isenko
 */
@Controller
public class NavigationController {
    @RequestMapping(value = {"/","/navigation"}, method = RequestMethod.GET)
    public String index(Model model, Authentication authentication) {
        model.addAttribute("authorities", authentication.getAuthorities().toString());
        return "navigation";
    }
}
