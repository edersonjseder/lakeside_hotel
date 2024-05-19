package com.lakesidehotel.controllers;

import com.lakesidehotel.dtos.BookedRoomDto;
import com.lakesidehotel.enums.Roles;
import com.lakesidehotel.security.annotation.HasProperAuthority;
import com.lakesidehotel.services.BookedRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookedRoomController {
    private final BookedRoomService bookedRoomService;

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/bookings/rooms/get/all/{roomId}")
    public ResponseEntity<List<BookedRoomDto>> getAllBookingsByRoomId(@PathVariable("roomId") Long roomId) {
        var bookings = bookedRoomService.getBookingsByRoomId(roomId);
        return ResponseEntity.ok(bookings);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/bookings/get/all")
    public ResponseEntity<List<BookedRoomDto>> getAllBookings() {
        var bookings = bookedRoomService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/bookings/get/confirmation/{confirmationCode}")
    public ResponseEntity<BookedRoomDto> getBookingByConfirmationCode(@PathVariable("confirmationCode") String confirmationCode) {
        var booking = bookedRoomService.fetchBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.ok(booking);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/bookings/get/{id}")
    public ResponseEntity<BookedRoomDto> getBookedRoomById(@PathVariable("id") Long id) {
        var booking = bookedRoomService.fetchBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @PostMapping("/bookings/room/{roomId}/add/new-booking")
    public ResponseEntity<String> addBookingRoom(@PathVariable("roomId") Long roomId, @RequestBody BookedRoomDto bookedRoomDto) throws IOException {
        var booking = bookedRoomService.saveBooking(roomId, bookedRoomDto);
        return ResponseEntity.ok(booking);
    }
}
