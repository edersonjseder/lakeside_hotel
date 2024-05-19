package com.lakesidehotel.exceptions;

import static com.lakesidehotel.constants.BookedRoomMessageConstants.BOOKED_ROOM_NOT_FOUND_MESSAGE;

public class BookedRoomNotFoundException extends RuntimeException {
    public BookedRoomNotFoundException(Long value) {
        super(generateMessage(value));
    }
    public BookedRoomNotFoundException(String value) {
        super(generateMessageCode(value));
    }

    private static String generateMessage(Long value) {
        return BOOKED_ROOM_NOT_FOUND_MESSAGE + value;
    }
    private static String generateMessageCode(String value) {
        return BOOKED_ROOM_NOT_FOUND_MESSAGE + value;
    }
}
