package com.lakesidehotel.exceptions;


import static com.lakesidehotel.constants.UserMessagesConstants.USER_INVALID_PASSWORD_MESSAGE;

public class PasswordException extends RuntimeException {

    public PasswordException() {
        super(USER_INVALID_PASSWORD_MESSAGE);
    }
}
