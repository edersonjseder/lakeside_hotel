package com.lakesidehotel.utils;

import com.lakesidehotel.dtos.ChangePasswordDto;
import com.lakesidehotel.dtos.EmailDto;
import com.lakesidehotel.dtos.PasswordForgottenDto;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    private static final String SUBJECT = "Reset password link";
    public EmailDto formatEmail(ChangePasswordDto changePasswordDto) {
        MailHtml mailHtml = new MailHtml();

        return EmailDto.builder()
                .sentFrom(changePasswordDto.getUsername())
                .sendTo(changePasswordDto.getEmail())
                .subject(SUBJECT)
                .message(mailHtml.formatChangedPasswordHtml(changePasswordDto.getFullName(), changePasswordDto.getMessage()))
                .build();
    }

    public EmailDto formatForgotPasswordEmail(PasswordForgottenDto passwordForgottenDto) {
        MailHtml mailHtml = new MailHtml();

        return EmailDto.builder()
                .sentFrom(passwordForgottenDto.getEmailFrom())
                .sendTo(passwordForgottenDto.getEmailTo())
                .subject(SUBJECT)
                .message(mailHtml.formatEmail(passwordForgottenDto.getLink(), passwordForgottenDto.getFullName()))
                .build();
    }
}
