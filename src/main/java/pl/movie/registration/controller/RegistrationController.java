package pl.movie.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.movie.registration.dto.UserData;
import pl.movie.registration.exception.UserAlreadyExistException;
import pl.movie.registration.model.Role;
import pl.movie.registration.service.RoleService;
import pl.movie.registration.service.UserService;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/register")
    public String register(final Model model) {
        model.addAttribute("userData", new UserData());
        return "register";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid UserData userData, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userData);
            return "register";
        }
        try {
            Role userRole = roleService.findByName("USER");
            userData.setRoles(Collections.singletonList(userRole));
            userService.register(userData);
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", "userData.email", "An account already exists for this email.");
            model.addAttribute("registrationForm", userData);
            return "register";
        }
        return "redirect:login";
    }
}
