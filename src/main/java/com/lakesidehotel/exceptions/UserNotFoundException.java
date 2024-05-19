package com.lakesidehotel.exceptions;

import java.util.UUID;

import static com.lakesidehotel.constants.UserMessagesConstants.USER_NOT_FOUND_MESSAGE;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID value) {
        super(generateMessage(value));
    }

    public UserNotFoundException(String value) {
        super(generateMessage(value));
    }

    private static String generateMessage(UUID value) {
        return USER_NOT_FOUND_MESSAGE + value;
    }
    private static String generateMessage(String value) {
        return USER_NOT_FOUND_MESSAGE + value;
    }
}
