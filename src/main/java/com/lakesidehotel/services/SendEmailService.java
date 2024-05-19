package com.lakesidehotel.services;

import com.lakesidehotel.dtos.EmailDto;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface SendEmailService {
    void sendEmail(EmailDto emailModel) throws MessagingException, UnsupportedEncodingException;
}
