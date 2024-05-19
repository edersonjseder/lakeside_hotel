package com.lakesidehotel.services;

import com.lakesidehotel.dtos.ForgotPasswordDto;
import com.lakesidehotel.responses.EmailResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface ForgotPasswordService {
    EmailResponse generateLinkAndSendEmailToUser(HttpServletRequest request, ForgotPasswordDto forgotPasswordDto);
}
