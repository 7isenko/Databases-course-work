package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.controllers.forms.LoginForm;
import io.github._7isenko.SCP1985.model.repositories.PersonnelEntityRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 7isenko
 */
@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final PersonnelEntityRepository personnelEntityRepository;

    public LoginController(AuthenticationManager authenticationManager, PersonnelEntityRepository personnelEntityRepository) {
        this.authenticationManager = authenticationManager;
        this.personnelEntityRepository = personnelEntityRepository;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        addLoginForm(model);

        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET, params = "error")
    public String loginWithError(@RequestParam("error") String err, Model model) {
        addLoginForm(model);

        model.addAttribute("error", "Неправильное имя или пароль.");
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET, params = "logout")
    public String loginWithLogout(@RequestParam("logout") String logout, Model model) {
        addLoginForm(model);

        model.addAttribute("logout", "Вы успешно вышли из профиля.");
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm") LoginForm loginForm) {
        String name = loginForm.getName();
        String key = loginForm.getKey();

        if (personnelEntityRepository.findPersonnelEntityByFullName(name) == null) {
            return "redirect:login?error";
        }

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(name, key);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        return "navigation";
    }

    private void addLoginForm(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
    }

}
