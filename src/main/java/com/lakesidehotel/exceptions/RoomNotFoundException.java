package com.lakesidehotel.exceptions;

import java.util.UUID;

import static com.lakesidehotel.constants.RoomMessageConstants.ROOM_NOT_FOUND_MESSAGE;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long value) {
        super(generateMessage(value));
    }

    private static String generateMessage(Long value) {
        return ROOM_NOT_FOUND_MESSAGE + value;
    }
}
