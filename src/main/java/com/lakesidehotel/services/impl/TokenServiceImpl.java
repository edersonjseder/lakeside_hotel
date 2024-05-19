package com.lakesidehotel.services.impl;

import com.lakesidehotel.dtos.TokenDto;
import com.lakesidehotel.exceptions.TokenNotFoundException;
import com.lakesidehotel.exceptions.UserNotFoundException;
import com.lakesidehotel.models.Token;
import com.lakesidehotel.repositories.TokenRepository;
import com.lakesidehotel.repositories.UserRepository;
import com.lakesidehotel.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationInMinutes;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    /**
     * Retrieves a password reset token for the given token id.
     * @param token The token to be returned
     * @return a password reset token if one is found or null if none is found.
     */
    @Override
    public Token fetchByToken(String token) {
        var resetToken = tokenRepository.findByToken(token);
        /**
         * Verifies if the service didn't return null Token object
         * */
        if (resetToken.isEmpty()) {
            throw new TokenNotFoundException(token);
        }

        return resetToken.get();
    }

    /**
     * Creates a new password reset token for the user identified by the given email.
     *
     * @param email The email uniquely identifying the user.
     * @return a new password reset token for the user identified by the given email or null if none was found.
     */
    @Transactional
    @Override
    public TokenDto createPasswordResetTokenForEmail(String email) {

        Token passwordResetToken;

        var user = userRepository.findUserByEmail(email);

        if (user.isPresent()){
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new Token(token, user.get(), now, tokenExpirationInMinutes);

            passwordResetToken = tokenRepository.save(passwordResetToken);
        } else {
            throw new UserNotFoundException(email);
        }

        return TokenDto.builder()
                .id(passwordResetToken.getId())
                .token(passwordResetToken.getToken())
                .user(user.get())
                .createdAt(passwordResetToken.getCreatedAt())
                .expiresAt(passwordResetToken.getExpiresAt())
                .build();
    }
}
