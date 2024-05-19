package com.lakesidehotel.utils;

import com.lakesidehotel.dtos.BookedRoomDto;
import com.lakesidehotel.models.BookedRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookedRoomUtils {
    private final RoomUtils roomUtils;
    private final DateUtils dateUtils;

    public BookedRoom toBookedRoom(BookedRoomDto bookedRoomDto) throws IOException {
        var bookedRoom = new BookedRoom();

        bookedRoom.setId(bookedRoomDto.getId());
        bookedRoom.setCheckInDate(dateUtils.parteToLocalDate(bookedRoomDto.getCheckInDate()));
        bookedRoom.setCheckOutDate(dateUtils.parteToLocalDate(bookedRoomDto.getCheckOutDate()));
        bookedRoom.setGuestFullName(bookedRoomDto.getGuestFullName());
        bookedRoom.setGuestEmail(bookedRoomDto.getGuestEmail());
        bookedRoom.setNumOfAdults(bookedRoomDto.getNumOfAdults());
        bookedRoom.setNumOfChildren(bookedRoomDto.getNumOfChildren());
        bookedRoom.setTotalNumOfGuests(bookedRoomDto.getTotalNumOfGuests());
        bookedRoom.setBookingConfirmationCode(bookedRoomDto.getBookingConfirmationCode());
        bookedRoom.setRoom(roomUtils.toRoom(bookedRoomDto.getRoom()));

        return bookedRoom;
    }
    
    public BookedRoomDto toBookedRoomDto(BookedRoom bookedRoom) {
        return BookedRoomDto.builder()
                .id(bookedRoom.getId())
                .checkInDate(dateUtils.parseDate(bookedRoom.getCheckInDate()))
                .checkOutDate(dateUtils.parseDate(bookedRoom.getCheckOutDate()))
                .guestFullName(bookedRoom.getGuestFullName())
                .guestEmail(bookedRoom.getGuestEmail())
                .numOfAdults(bookedRoom.getNumOfAdults())
                .numOfChildren(bookedRoom.getNumOfChildren())
                .totalNumOfGuests(bookedRoom.getTotalNumOfGuests())
                .bookingConfirmationCode(bookedRoom.getBookingConfirmationCode())
                .room(roomUtils.toRoomDto(bookedRoom.getRoom()))
                .build();
    }

    public List<BookedRoomDto> toListBookedRoomDto(List<BookedRoom> bookedRooms) {
        return bookedRooms.stream().map(bookedRoom -> BookedRoomDto.builder()
                .id(bookedRoom.getId())
                .checkInDate(dateUtils.parseDate(bookedRoom.getCheckInDate()))
                .checkOutDate(dateUtils.parseDate(bookedRoom.getCheckOutDate()))
                .guestFullName(bookedRoom.getGuestFullName())
                .guestEmail(bookedRoom.getGuestEmail())
                .numOfAdults(bookedRoom.getNumOfAdults())
                .numOfChildren(bookedRoom.getNumOfChildren())
                .totalNumOfGuests(bookedRoom.getTotalNumOfGuests())
                .bookingConfirmationCode(bookedRoom.getBookingConfirmationCode())
                .room(roomUtils.toRoomDto(bookedRoom.getRoom()))
                .build()).collect(Collectors.toList());
    }
}
