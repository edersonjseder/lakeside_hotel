package com.lakesidehotel.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.lakesidehotel.dtos.UserDto;
import com.lakesidehotel.enums.Roles;
import com.lakesidehotel.security.annotation.HasProperAuthority;
import com.lakesidehotel.services.UserService;
import com.lakesidehotel.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.lakesidehotel.constants.UserMessagesConstants.USER_REMOVED_SUCCESS_MESSAGE;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserUtils userUtils;

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/users/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var userPage = userService.findAllUsers();

        return ResponseEntity.ok(userPage);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(userUtils.toUserDto(userService.findUserById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN, CUSTOMER')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> removeUserById(@PathVariable(value = "id") UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(USER_REMOVED_SUCCESS_MESSAGE, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN, CUSTOMER')")
    @PutMapping(value = "/users/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Validated(UserDto.UserView.UserPut.class) @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(userDto));
    }
}
