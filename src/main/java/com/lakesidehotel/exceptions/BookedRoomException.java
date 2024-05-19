package com.lakesidehotel.exceptions;

public class BookedRoomException extends RuntimeException {
    public BookedRoomException(String value) {
        super(value);
    }
}
