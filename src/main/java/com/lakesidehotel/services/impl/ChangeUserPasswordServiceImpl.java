package com.lakesidehotel.services.impl;

import com.lakesidehotel.dtos.ResetPasswordDto;
import com.lakesidehotel.exceptions.PasswordException;
import com.lakesidehotel.exceptions.PasswordsNotMatchException;
import com.lakesidehotel.exceptions.UserNotFoundException;
import com.lakesidehotel.repositories.UserRepository;
import com.lakesidehotel.responses.PasswordResponse;
import com.lakesidehotel.services.ChangeUserPasswordService;
import com.lakesidehotel.services.SendEmailService;
import com.lakesidehotel.utils.EmailUtils;
import com.lakesidehotel.utils.PasswordUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ChangeUserPasswordServiceImpl implements ChangeUserPasswordService {
    private final PasswordUtils passwordUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SendEmailService sendEmailService;
    private final EmailUtils emailUtils;

    @Transactional
    @Override
    public PasswordResponse updateUserPassword(ResetPasswordDto resetPasswordDto) {
        var user = userRepository.findUserByEmail(resetPasswordDto.getEmail()).orElseThrow(() -> new UserNotFoundException(resetPasswordDto.getEmail()));

        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new PasswordsNotMatchException();
        }

        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            throw new PasswordException();
        }

        var hashedPassword = passwordEncoder.encode(resetPasswordDto.getPassword());

        user.setPassword(hashedPassword);
        user.setCurrentPasswordDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        try {
            sendEmailService.sendEmail(emailUtils.formatEmail(passwordUtils.toChangePasswordDto(user)));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.getStackTrace();
        }

        return passwordUtils.toPasswordResponse(user);
    }
}
