package com.lakesidehotel.controllers;


import com.lakesidehotel.dtos.ForgotPasswordDto;
import com.lakesidehotel.responses.EmailResponse;
import com.lakesidehotel.services.ForgotPasswordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping(path = "/password/forgot")
    public ResponseEntity<EmailResponse> forgotPasswordPost(HttpServletRequest request, @RequestBody ForgotPasswordDto forgotDto) {
        var value = forgotPasswordService.generateLinkAndSendEmailToUser(request, forgotDto);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }
}
