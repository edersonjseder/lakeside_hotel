package com.lakesidehotel.services;

import com.lakesidehotel.dtos.BookedRoomDto;

import java.io.IOException;
import java.util.List;

public interface BookedRoomService {
    List<BookedRoomDto> getBookingsByRoomId(Long roomId);

    BookedRoomDto fetchBookingById(Long id);

    List<BookedRoomDto> getAllBookings();

    String saveBooking(Long roomId, BookedRoomDto bookedRoomDto) throws IOException;

    BookedRoomDto fetchBookingByConfirmationCode(String confirmationCode);
}
