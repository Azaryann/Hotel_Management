package am.itspace.hotelManagement.repository;

import am.itspace.hotelManagement.enums.RoomStatus;
import am.itspace.hotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
  List<Room> findByRoomStatus(RoomStatus roomStatus);
}
