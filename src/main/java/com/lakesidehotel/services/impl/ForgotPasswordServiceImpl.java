package com.lakesidehotel.services.impl;

import com.lakesidehotel.dtos.EmailDto;
import com.lakesidehotel.dtos.ForgotPasswordDto;
import com.lakesidehotel.responses.EmailResponse;
import com.lakesidehotel.services.ForgotPasswordService;
import com.lakesidehotel.services.SendEmailService;
import com.lakesidehotel.services.TokenService;
import com.lakesidehotel.utils.EmailUtils;
import com.lakesidehotel.utils.PasswordUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final SendEmailService sendEmailService;
    private final EmailUtils emailUtils;
    private final PasswordUtils passwordUtils;
    private final TokenService tokenService;
    private static final String OK = "Instructions sent to email {0}";
    @Value("${authuser.client.url}")
    private String clientUrl;

    /**
     * This method gets the passwordResetToken object to make the proper link, so the
     * user can change his password
     *
     * @param request            to be used to generate the forgot password link
     * @param forgotPasswordDto The password object parameter
     */
    @Override
    public EmailResponse generateLinkAndSendEmailToUser(HttpServletRequest request, ForgotPasswordDto forgotPasswordDto) {
        var passwordResetToken = tokenService.createPasswordResetTokenForEmail(forgotPasswordDto.getEmail());

        // Gets the userId & it gets the token from the object.
        var user = passwordResetToken.getUser();
        var token = passwordResetToken.getToken();

        var resetPasswordUrl = passwordUtils.createPasswordResetUrl(request, clientUrl, user.getId(), token);

        try {
            sendEmailService.sendEmail(emailUtils.formatForgotPasswordEmail(passwordUtils.toPasswordForgottenDto(user, resetPasswordUrl)));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.getStackTrace();
        }

        return EmailResponse.builder().emailSent(MessageFormat.format(OK, user.getEmail())).build();
    }
}
