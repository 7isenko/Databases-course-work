package io.github._7isenko.SCP1985.controllers;

import io.github._7isenko.SCP1985.controllers.forms.LoginForm;
import io.github._7isenko.SCP1985.model.repositories.PersonnelEntityRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);

        return "login";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.POST)
    public ResponseEntity<String> login(@ModelAttribute("loginForm") LoginForm loginForm) {
        String name = loginForm.getName();
        String key = loginForm.getKey();

        if (personnelEntityRepository.findPersonnelEntityByFullName(name) == null) {
            return new ResponseEntity<>(("Invalid."), HttpStatus.UNAUTHORIZED);
        }

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(name, key);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/main"); // TODO: сделать страничку с навигацией?
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
