package com.spring.guitarsite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordForm {
    @NotBlank
    @Size(min = 8, max = 64)
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
