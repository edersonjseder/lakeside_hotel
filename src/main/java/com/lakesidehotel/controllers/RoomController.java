package com.lakesidehotel.controllers;

import com.lakesidehotel.dtos.RoomDto;
import com.lakesidehotel.dtos.RoomTypeDto;
import com.lakesidehotel.enums.Roles;
import com.lakesidehotel.security.annotation.HasProperAuthority;
import com.lakesidehotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.lakesidehotel.constants.RoomMessageConstants.ROOM_REMOVED_SUCCESS_MESSAGE;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/rooms/get/all-rooms")
    public ResponseEntity<List<RoomDto>> fetchAllRooms() {
        var rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/rooms/get/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(roomService.getOneRoom(id));
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/rooms/get/room-types")
    public ResponseEntity<Set<RoomTypeDto>> getRoomTypes() {
        return ResponseEntity.ok(roomService.fetchRoomTypes());
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN, Roles.ROLE_CUSTOMER})
    @GetMapping("/rooms/get/available")
    public ResponseEntity<List<RoomDto>> getAvailableRooms(@RequestParam("checkInDate") String checkInDate,
                                                           @RequestParam("checkOutDate") String checkOutDate,
                                                           @RequestParam("roomType") String roomType) {
        var availableRooms = roomService.fetchAvailableRooms(checkInDate, checkOutDate, roomType);
        return availableRooms.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availableRooms);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN})
    @PostMapping("/rooms/add/new-room")
    public ResponseEntity<RoomDto> addNewRoom(@RequestParam("photo") MultipartFile photo,
                                              @RequestParam("roomType") String roomType,
                                              @RequestParam("roomPrice") BigDecimal roomPrice) throws IOException {
        var room = roomService.saveRoom(RoomDto.builder().photoUrl(photo).roomType(roomType).roomPrice(roomPrice).build());
        return ResponseEntity.ok(room);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN})
    @PutMapping("/rooms/update/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("id") Long id,
                                              @RequestParam(required = false) MultipartFile photo,
                                              @RequestParam(required = false) String roomType,
                                              @RequestParam(required = false) BigDecimal roomPrice) throws IOException {
        var room = roomService.saveRoom(RoomDto.builder().id(id).photoUrl(photo).roomType(roomType).roomPrice(roomPrice).build());
        return ResponseEntity.ok(room);
    }

    @HasProperAuthority(authorities = {Roles.ROLE_ADMIN})
    @DeleteMapping(value = "/rooms/remove/{id}")
    public ResponseEntity<String> removeRoom(@PathVariable("id") Long id) {
        roomService.removeRoom(id);
        return ResponseEntity.ok(ROOM_REMOVED_SUCCESS_MESSAGE);
    }
}
