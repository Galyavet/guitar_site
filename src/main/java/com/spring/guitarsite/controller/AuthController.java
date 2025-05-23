package com.spring.guitarsite.controller;

import com.spring.guitarsite.dto.UserDto;
import com.spring.guitarsite.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                            HttpServletRequest request,
                        Model model) {
        if (error != null) {
            String errorMessage = (String) request.getSession()
                    .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            model.addAttribute("error", errorMessage != null ? errorMessage : "Неверные учетные данные");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }
        return "login";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto userDto,
                           BindingResult bindingResult,
                           Model model) {
        model.addAttribute("user", new UserDto());

        if (userService.existsByEmail(userDto.getEmail())) {
            bindingResult.rejectValue("email", "email.exists", "Этот email уже зарегистрирован");
        }
        if (userService.existsByUsername(userDto.getUsername())) {
            bindingResult.rejectValue("username", "username.exists", "Этот логин уже занят");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationError", "Пользователь с такими данными уже существует");
            return "register";
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.registerNewUser(userDto);

        return "redirect:/login?registered";
    }

}
