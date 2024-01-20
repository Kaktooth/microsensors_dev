package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.Authority;
import com.projects.microsensors.model.User;
import com.projects.microsensors.model.UserDTO;
import com.projects.microsensors.common.auth.Authorities;
import com.projects.microsensors.service.sensor.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String createNewUser(@Valid @ModelAttribute("user") UserDTO userInput,
                                BindingResult bindingResult) {


        var username = userInput.getUsername();

        if (userService.usernameExists(username)) {
            bindingResult.addError(new FieldError("userInput", "username", "User with this name already exists"));
        }

        if (bindingResult.hasErrors()) {
            return "sign-up";
        } else {
            var encodedPassword = passwordEncoder.encode(userInput.getPassword());

            var user = User
                    .builder()
                    .username(username)
                    .password(encodedPassword)
                    .enabled(true)
                    .build();

            var createdUser = userService.register(user);
            var authority = new Authority(username, Authorities.USER.ordinal(),
                    createdUser.getId());
            userService.registerAuthority(authority);

            return "redirect:/log-in";
        }
    }
}
