package com.lakesidehotel.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordForgottenDto {
    private String emailFrom;
    private String emailTo;
    private String fullName;
    private String link;
    private String actionType;
}
