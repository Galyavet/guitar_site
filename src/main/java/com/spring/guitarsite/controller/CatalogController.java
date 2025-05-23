package com.spring.guitarsite.controller;

import com.spring.guitarsite.model.Guitar;
import com.spring.guitarsite.service.GuitarService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private final GuitarService guitarService;

    public CatalogController(GuitarService guitarService) {
        this.guitarService = guitarService;
    }

    @GetMapping
    public String showCatalog(
            @RequestParam(required = false) List<String> types,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            Model model) {

        Page<Guitar> guitars = guitarService.findByFilters(types, brands, page, size);

        model.addAttribute("guitars", guitars);
        model.addAttribute("types", guitarService.findAllTypeNames());
        model.addAttribute("brands", guitarService.findAllBrands());
        model.addAttribute("currentTypes", types != null ? types : Collections.emptyList());
        model.addAttribute("currentBrands", brands != null ? brands : Collections.emptyList());

        return "catalog";
    }

    @GetMapping("/filter")
    public String filterCatalog(
            @RequestParam(required = false) String types,
            @RequestParam(required = false) String brands,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        List<String> typeList = types != null ? Arrays.asList(types.split(",")) : Collections.emptyList();
        List<String> brandList = brands != null ? Arrays.asList(brands.split(",")) : Collections.emptyList();

        Page<Guitar> guitars = guitarService.findByFilters(typeList, brandList, page, 9);

        model.addAttribute("guitars", guitars);
        return "catalog :: #guitarsContainer";
    }

}