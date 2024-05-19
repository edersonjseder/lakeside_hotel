package com.lakesidehotel.services.impl;

import com.lakesidehotel.dtos.RoomDto;
import com.lakesidehotel.dtos.RoomTypeDto;
import com.lakesidehotel.exceptions.RoomNotFoundException;
import com.lakesidehotel.models.Room;
import com.lakesidehotel.repositories.RoomRepository;
import com.lakesidehotel.services.RoomService;
import com.lakesidehotel.utils.DateUtils;
import com.lakesidehotel.utils.RoomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomUtils roomUtils;
    private final DateUtils dateUtils;

    @Transactional
    @Override
    public RoomDto saveRoom(RoomDto roomDto) throws IOException {
        Room room;

        if (roomDto.getId() == null) {
            room = new Room();

            BeanUtils.copyProperties(roomDto, room);

            if (!roomDto.getPhotoUrl().isEmpty()) {
                room.setPhoto(roomDto.getPhotoUrl().getBytes());
            }

        } else {
            room = roomRepository.findById(roomDto.getId()).orElseThrow(() -> new RoomNotFoundException(roomDto.getId()));

            if (!roomDto.getPhotoUrl().isEmpty()) {
                room.setPhoto(roomDto.getPhotoUrl().getBytes());
            }

            room.setRoomType(roomDto.getRoomType());
            room.setRoomPrice(roomDto.getRoomPrice());
            room.setPhoto(room.getPhoto());
        }

        return  roomUtils.toRoomDto(roomRepository.save(room));
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomUtils.toListRoomDto(roomRepository.findAll());
    }

    @Override
    public RoomDto getOneRoom(Long id) {
        return roomUtils.toRoomDto(roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id)));
    }

    @Transactional
    @Override
    public void removeRoom(Long id) {
        var room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
        roomRepository.delete(room);
    }

    @Override
    public List<RoomDto> fetchAvailableRooms(String checkInDate, String checkOutDate, String roomType) {
        var rooms = roomRepository.findAvailableRoomsByDatesAndType(dateUtils.parteToLocalDate(checkInDate),
                                                                    dateUtils.parteToLocalDate(checkOutDate), roomType);

       return  roomUtils.toListRoomDto(rooms);
    }

    @Override
    public Set<RoomTypeDto> fetchRoomTypes() {
        return roomUtils.toListRoomTypes(roomRepository.findAllByRoomType());
    }
}
