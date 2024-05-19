package com.lakesidehotel.services.impl;

import com.lakesidehotel.dtos.BookedRoomDto;
import com.lakesidehotel.exceptions.BookedRoomException;
import com.lakesidehotel.exceptions.BookedRoomNotFoundException;
import com.lakesidehotel.exceptions.RoomNotFoundException;
import com.lakesidehotel.models.BookedRoom;
import com.lakesidehotel.repositories.BookedRoomRepository;
import com.lakesidehotel.repositories.RoomRepository;
import com.lakesidehotel.services.BookedRoomService;
import com.lakesidehotel.utils.BookedRoomUtils;
import com.lakesidehotel.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.lakesidehotel.constants.BookedRoomMessageConstants.BOOKED_ROOM_INVALID_MESSAGE;
import static com.lakesidehotel.constants.BookedRoomMessageConstants.BOOKED_ROOM_UNAVAILABLE_MESSAGE;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl implements BookedRoomService {
    private final BookedRoomRepository bookedRoomRepository;
    private final RoomRepository roomRepository;
    private final BookedRoomUtils bookedRoomUtils;
    private final DateUtils dateUtils;

    @Override
    public List<BookedRoomDto> getBookingsByRoomId(Long roomId) {
        return bookedRoomUtils.toListBookedRoomDto(bookedRoomRepository.findBookedRoomIntoRoom(roomId));
    }

    @Override
    public BookedRoomDto fetchBookingById(Long id) {
        return bookedRoomUtils.toBookedRoomDto(bookedRoomRepository.findById(id).orElseThrow(() -> new BookedRoomNotFoundException(id)));
    }

    @Override
    public List<BookedRoomDto> getAllBookings() {
        return bookedRoomUtils.toListBookedRoomDto(bookedRoomRepository.findAll());
    }

    @Override
    public String saveBooking(Long roomId, BookedRoomDto bookedRoomDto) throws IOException {
        if (dateUtils.isBefore(dateUtils.parteToLocalDate(bookedRoomDto.getCheckOutDate()),
                               dateUtils.parteToLocalDate(bookedRoomDto.getCheckInDate()))) {
            throw new BookedRoomException(BOOKED_ROOM_INVALID_MESSAGE);
        }

        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        var existingBookings = room.getBookings();

        var booking = bookedRoomUtils.toBookedRoom(bookedRoomDto);

        boolean isAvailable = isRoomAvailable(booking, existingBookings);

        if (isAvailable) {
            room.addBooking(booking);
            bookedRoomRepository.save(booking);
        } else {
            throw new BookedRoomException(BOOKED_ROOM_UNAVAILABLE_MESSAGE);
        }

        return booking.getBookingConfirmationCode();
    }

    private boolean isRoomAvailable(BookedRoom bookedRoom, Set<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBook ->
                    bookedRoom.getCheckInDate().equals(existingBook.getCheckInDate())
                        || bookedRoom.getCheckOutDate().isBefore(existingBook.getCheckOutDate())
                        || (bookedRoom.getCheckInDate().isAfter(existingBook.getCheckInDate())
                        && bookedRoom.getCheckInDate().isBefore(existingBook.getCheckOutDate()))
                        || (bookedRoom.getCheckInDate().isBefore(existingBook.getCheckInDate())
                        && bookedRoom.getCheckOutDate().equals(existingBook.getCheckOutDate()))
                        || (bookedRoom.getCheckInDate().isBefore(existingBook.getCheckInDate())
                        && bookedRoom.getCheckOutDate().isAfter(existingBook.getCheckOutDate()))
                        || (bookedRoom.getCheckInDate().equals(existingBook.getCheckOutDate())
                        && bookedRoom.getCheckOutDate().equals(existingBook.getCheckInDate()))
                        || (bookedRoom.getCheckInDate().equals(existingBook.getCheckOutDate())
                        && bookedRoom.getCheckOutDate().equals(bookedRoom.getCheckInDate()))
                );
    }

    @Override
    public BookedRoomDto fetchBookingByConfirmationCode(String confirmationCode) {
        return bookedRoomUtils
                .toBookedRoomDto(bookedRoomRepository
                        .findByBookingConfirmationCode(confirmationCode)
                        .orElseThrow(() -> new BookedRoomNotFoundException(confirmationCode)));
    }
}
