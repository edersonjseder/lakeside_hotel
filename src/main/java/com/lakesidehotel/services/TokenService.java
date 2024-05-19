package com.lakesidehotel.services;

import com.lakesidehotel.dtos.TokenDto;
import com.lakesidehotel.models.Token;

public interface TokenService {
    public Token fetchByToken(String token);
    TokenDto createPasswordResetTokenForEmail(String email);
}
