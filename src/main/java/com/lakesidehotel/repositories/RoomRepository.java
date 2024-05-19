package com.lakesidehotel.repositories;

import com.lakesidehotel.dtos.RoomTypeDto;
import com.lakesidehotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN " +
           "(SELECT br.room.id FROM BookedRoom br WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate)))")
    List<Room> findAvailableRoomsByDatesAndType(LocalDateTime checkInDate, LocalDateTime checkOutDate, String roomType);

    @Query("SELECT r.roomType FROM Room r ")
    Set<String> findAllByRoomType();
}
