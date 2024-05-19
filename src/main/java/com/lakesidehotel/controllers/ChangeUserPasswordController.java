package com.lakesidehotel.controllers;

import com.lakesidehotel.dtos.ResetPasswordDto;
import com.lakesidehotel.responses.PasswordResponse;
import com.lakesidehotel.services.ChangeUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangeUserPasswordController {
    private final ChangeUserPasswordService changeUserPasswordService;

    @PutMapping(value = "/password/change")
    public ResponseEntity<PasswordResponse> updatePassword(@RequestBody ResetPasswordDto resetPassword) {
        return ResponseEntity.status(HttpStatus.OK).body(changeUserPasswordService.updateUserPassword(resetPassword));
    }
}
