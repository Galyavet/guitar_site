package com.spring.guitarsite.controller;

import com.spring.guitarsite.dto.ForgotPasswordForm;
import com.spring.guitarsite.dto.ResetPasswordForm;
import com.spring.guitarsite.exception.EmailSendException;
import com.spring.guitarsite.model.PasswordResetToken;
import com.spring.guitarsite.model.User;
import com.spring.guitarsite.repository.PasswordResetTokenRepository;
import com.spring.guitarsite.service.EmailService;
import com.spring.guitarsite.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PasswordResetController {

    private final UserServiceImpl userService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetController(UserServiceImpl userService,
                                   EmailService emailService,
                                   PasswordResetTokenRepository tokenRepository) {
        this.userService = userService;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@Valid ForgotPasswordForm form,
                                        BindingResult result,
                                        HttpServletRequest request) throws EmailSendException {
        if (result.hasErrors()) {
            return "forgot-password";
        }

        User user = userService.findByEmail(form.getEmail());
        if (user == null) {
            result.rejectValue("email", "email.not.exists", "Пользователь с таким email не найден");
            return "forgot-password";
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String resetUrl = getAppUrl(request) + "/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(user.getEmail(), resetUrl);

        return "redirect:/forgot-password?success";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            return "redirect:/forgot-password?error";
        }

        model.addAttribute("resetPasswordForm", new ResetPasswordForm());
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@Valid ResetPasswordForm form,
                                       BindingResult result,
                                       @RequestParam("token") String token) {
        if (result.hasErrors()) {
            return "reset-password";
        }

        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            return "redirect:/forgot-password?error";
        }

        User user = resetToken.getUser();
        userService.changeUserPassword(user, form.getNewPassword());
        tokenRepository.delete(resetToken);

        return "redirect:/login?resetSuccess";
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
