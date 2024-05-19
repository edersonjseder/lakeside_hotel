package com.lakesidehotel.services;

import com.lakesidehotel.dtos.RoomDto;
import com.lakesidehotel.dtos.RoomTypeDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface RoomService {

    RoomDto saveRoom(RoomDto roomDto) throws IOException;

    List<RoomDto> getAllRooms();

    RoomDto getOneRoom(Long id);

    void removeRoom(Long id);

    List<RoomDto> fetchAvailableRooms(String checkInDate, String checkOutDate, String roomType);

    Set<RoomTypeDto> fetchRoomTypes();
}
