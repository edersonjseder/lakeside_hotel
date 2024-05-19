package com.lakesidehotel.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.lakesidehotel.dtos.SignInDto;
import com.lakesidehotel.dtos.UserDto;
import com.lakesidehotel.responses.TokenResponse;
import com.lakesidehotel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping(value = "/users/auth/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) throws IOException {
        log.debug("POST signUp userDto received {} ", userDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto));
    }

    @PostMapping(value = "/users/auth/signIn")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }
}
