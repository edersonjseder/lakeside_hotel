package com.lakesidehotel.utils;

import com.lakesidehotel.dtos.RoomDto;
import com.lakesidehotel.dtos.RoomTypeDto;
import com.lakesidehotel.models.Room;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomUtils {
    public Room toRoom(RoomDto roomDto) throws IOException {
        var room = new Room();

        room.setId(roomDto.getId());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomPrice(roomDto.getRoomPrice());
        room.setPhoto(roomDto.getPhotoUrl().getBytes());

        return room;
    }
    public RoomDto toRoomDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomPrice(room.getRoomPrice())
                .photo(encodeToString(room.getPhoto()))
                .build();
    }

    public List<RoomDto> toListRoomDto(List<Room> rooms) {
        return rooms.stream().map(room -> RoomDto.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomPrice(room.getRoomPrice())
                .photo(encodeToString(room.getPhoto()))
                .build()).collect(Collectors.toList());
    }

    public Set<RoomTypeDto> toListRoomTypes(Set<String> rooms) {
        return rooms.stream().map(roomType -> RoomTypeDto.builder().roomType(roomType).build()).collect(Collectors.toSet());
    }

    private String encodeToString(byte[] photo) {
        return Base64.encodeBase64String(photo);
    }
}
