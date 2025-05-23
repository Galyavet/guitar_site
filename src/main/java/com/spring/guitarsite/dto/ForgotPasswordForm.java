package com.spring.guitarsite.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordForm {
    @Email
    @NotBlank
    private String email;
}
