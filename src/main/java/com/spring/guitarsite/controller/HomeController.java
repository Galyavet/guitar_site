package com.spring.guitarsite.controller;


import com.spring.guitarsite.service.GuitarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GuitarService guitarService;


    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("guitars", guitarService.getAllGuitars());
        return "index";
    }
}