package com.lakesidehotel.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookedRoomDto {
    private Long id;
    private String checkInDate;
    private String checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private Integer numOfAdults;
    private Integer numOfChildren;
    private Integer totalNumOfGuests;
    private String bookingConfirmationCode;
    private RoomDto room;
}
