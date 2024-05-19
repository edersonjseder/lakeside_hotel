package com.lakesidehotel.repositories;

import com.lakesidehotel.models.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    @Query(value = "select * from booked_room where room_id = :roomId", nativeQuery = true)
    List<BookedRoom> findBookedRoomIntoRoom(@Param("roomId") Long roomId);

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);
}
