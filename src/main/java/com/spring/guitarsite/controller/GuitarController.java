package com.spring.guitarsite.controller;

import com.spring.guitarsite.dto.ReviewFormDto;
import com.spring.guitarsite.model.Guitar;
import com.spring.guitarsite.model.User;
import com.spring.guitarsite.service.GuitarService;
import com.spring.guitarsite.service.ReviewService;
import com.spring.guitarsite.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/guitars")
@RequiredArgsConstructor
public class GuitarController {

    private final GuitarService guitarService;
    private final ReviewService reviewService;
    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public String getGuitarPage(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Guitar guitar = guitarService.getGuitarById(id);
        List<Guitar> similarGuitars = guitarService.getSimilarGuitarsByType(guitar.getType(), id);

        model.addAttribute("reviewForm", new ReviewFormDto());
        model.addAttribute("reviews", reviewService.getReviewsForGuitar(id));
        model.addAttribute("guitar", guitar);
        model.addAttribute("similarGuitars", similarGuitars);

        if (userDetails != null) {
            User user = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("isFavorite", user.getFavoriteGuitars().contains(guitar));
        }

        return "guitar";
    }

    @GetMapping
    public String getAllGuitars(Model model) {
        model.addAttribute("guitars", guitarService.getAllGuitars());
        return "guitar";
    }

    @PostMapping("/{id}/reviews")
    public String addReview(@PathVariable Long id, @Valid ReviewFormDto reviewFormDto,
                            BindingResult result, @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "redirect:/guitars/" + id + "?reviewError";
        }

        User user = userService.findByUsername(userDetails.getUsername());
        reviewService.addReview(id, user, reviewFormDto);

        return "redirect:/guitars/" + id;
    }

    @PostMapping("/{id}/toggle-favorite")
    public String toggleFavorite(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        guitarService.toggleFavoriteGuitar(id, user);

        return "redirect:/guitars/" + id;
    }
}