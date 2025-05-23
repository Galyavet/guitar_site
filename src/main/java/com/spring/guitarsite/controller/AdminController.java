package com.spring.guitarsite.controller;

import com.spring.guitarsite.model.Guitar;
import com.spring.guitarsite.model.GuitarType;
import com.spring.guitarsite.model.User;
import com.spring.guitarsite.service.GuitarService;
import com.spring.guitarsite.service.GuitarTypeService;
import com.spring.guitarsite.service.impl.ImageStorageService;
import com.spring.guitarsite.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.usertype.UserType;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final GuitarService guitarService;

    private final GuitarTypeService guitarTypeService;

    private final UserServiceImpl userService;



    private final ImageStorageService imageStorageService;

    public AdminController(GuitarService guitarService, GuitarTypeService guitarTypeService, UserServiceImpl userService, ImageStorageService imageStorageService) {
        this.guitarService = guitarService;
        this.guitarTypeService = guitarTypeService;
        this.userService = userService;
        this.imageStorageService = imageStorageService;
    }

    @GetMapping
    public String adminDashboard() {
        return "admin/dashboard";
    }
    @GetMapping("/guitars")
    public String listGuitars(
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        List<Guitar> guitars = guitarService.findAllSorted(sort);

        model.addAttribute("guitars", guitars);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        return "admin/guitars";
    }

    @GetMapping("/guitars/add")
    public String showAddForm(Model model) {
        model.addAttribute("guitar", new Guitar());
        model.addAttribute("guitarTypes", guitarTypeService.findAll());
        return "admin/add-guitar";
    }

    @PostMapping("/guitars/add")
    public String addGuitar(@ModelAttribute Guitar guitar, @RequestParam Long typeId, @RequestParam("imageFile") MultipartFile imageFile,@RequestParam(value = "currentImage", required = false) String currentImage, Principal principal) throws IOException {
        GuitarType type = guitarTypeService.findById(typeId);

        guitar.setType(type);

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            guitar.setAddedBy(user);
        }

        String imagePath = imageStorageService.saveImage(imageFile, currentImage);
        guitar.setImageUrl(imagePath);

        guitarService.saveGuitar(guitar);
        return "redirect:/admin/guitars";
    }

    @GetMapping("/guitars/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Guitar guitar = guitarService.getGuitarById(id);
        model.addAttribute("guitar", guitar);
        model.addAttribute("guitarTypes", guitarTypeService.findAll());
        return "admin/edit-guitar";
    }

    @PostMapping("/guitars/edit/{id}")
    public String updateGuitar(@PathVariable Long id, @ModelAttribute Guitar guitar, @RequestParam Long typeId, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                               @RequestParam(value = "currentImage", required = false) String currentImage, Principal principal) throws IOException {
        GuitarType type = guitarTypeService.findById(typeId);
        guitar.setType(type);
        guitar.setId(id);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = imageStorageService.saveImage(imageFile, currentImage);
            guitar.setImageUrl(imagePath);
        } else if (currentImage != null) {
            guitar.setImageUrl(currentImage);
        }

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            guitar.setAddedBy(user);
        }
        guitarService.updateGuitar(guitar);
        return "redirect:/admin/guitars";
    }

    @GetMapping("/guitars/delete/{id}")
    public String deleteGuitar(@PathVariable Long id) {
        guitarService.deleteGuitar(id);
        return "redirect:/admin/guitars";
    }

    @GetMapping("/users")
    public String listUsers(
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        List<User> users = userService.findAllSorted(sort);

        model.addAttribute("users", users);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        return "admin/users";
    }


}