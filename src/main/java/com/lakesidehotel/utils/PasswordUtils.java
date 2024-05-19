package com.lakesidehotel.utils;

import com.lakesidehotel.dtos.ChangePasswordDto;
import com.lakesidehotel.dtos.PasswordForgottenDto;
import com.lakesidehotel.models.User;
import com.lakesidehotel.responses.PasswordResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.lakesidehotel.constants.UserMessagesConstants.USER_PASSWORD_SUCCESS_MESSAGE;

@Component
@RequiredArgsConstructor
public class PasswordUtils {
    @Value("${mail.smtp.username}")
    private String emailFrom;
    private final DateUtils dateUtils;
    private static final String CHANGE_PASSWORD_PATH = "/password/change";

    /**
     * Builds and returns the URL to reset the user password and send by email.
     *
     * @param request   The Http Servlet Request.
     * @param clientUrl the frontend url
     * @param userId    The user id.
     * @param token     The token
     * @return the URL to reset the user password.
     */
    public String createPasswordResetUrl(HttpServletRequest request, String clientUrl, UUID userId, String token) {

        return request.getScheme() +
                "://" +
                clientUrl +
                CHANGE_PASSWORD_PATH +
                "?token=" +
                token +
                "&userId=" +
                userId;
    }

    public ChangePasswordDto toChangePasswordDto(User user) {
        return ChangePasswordDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .message(USER_PASSWORD_SUCCESS_MESSAGE)
                .build();
    }

    public PasswordForgottenDto toPasswordForgottenDto(User user, String link) {
        return PasswordForgottenDto.builder()
                .emailFrom(emailFrom)
                .emailTo(user.getEmail())
                .fullName(user.getFullName())
                .link(link)
                .build();
    }

    public PasswordResponse toPasswordResponse(User user) {
        return PasswordResponse.builder()
                .message(USER_PASSWORD_SUCCESS_MESSAGE)
                .currentPasswordDate(dateUtils.parseDate(user.getCurrentPasswordDate()))
                .lastUpdatedDate(dateUtils.parseDate(user.getLastUpdateDate()))
                .build();
    }
}
