package com.lakesidehotel.exceptions;


import static com.lakesidehotel.constants.RoleMessagesConstants.ROLE_NOT_FOUND_MESSAGE;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super(ROLE_NOT_FOUND_MESSAGE);
    }
}
