package com.lakesidehotel.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordDto {
    @NotBlank(message = "E-mail is required")
    @Email(message = "Insert valid e-mail")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank(message = "Old Password is required")
    @Size(min = 6, max = 20)
    private String oldPassword;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 6, max = 20)
    private String confirmPassword;
}
