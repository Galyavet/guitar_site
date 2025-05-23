package com.spring.guitarsite.controller;

import com.spring.guitarsite.model.User;
import com.spring.guitarsite.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserServiceImpl userService;

    public ProfileController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model, Principal principal) {

        String username = principal.getName();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "profile";
    }
}

