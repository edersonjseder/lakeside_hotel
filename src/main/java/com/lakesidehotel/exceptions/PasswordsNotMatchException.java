package com.lakesidehotel.exceptions;


import static com.lakesidehotel.constants.UserMessagesConstants.USER_PASSWORDS_DONT_MATCH_MESSAGE;

public class PasswordsNotMatchException extends RuntimeException {

    public PasswordsNotMatchException() {
        super(USER_PASSWORDS_DONT_MATCH_MESSAGE);
    }
}
