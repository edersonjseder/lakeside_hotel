package com.lakesidehotel.services;

import com.lakesidehotel.dtos.SignInDto;
import com.lakesidehotel.dtos.UserDto;
import com.lakesidehotel.models.User;
import com.lakesidehotel.responses.TokenResponse;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAllUsers();
    User findUserById(UUID id);
    TokenResponse signIn(SignInDto request) throws BadCredentialsException;
    UserDto saveUser(UserDto userDto) throws IOException;
    void deleteUser(UUID id);
}
