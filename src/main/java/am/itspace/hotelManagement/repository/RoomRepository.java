package am.itspace.hotelManagement.repository;

import am.itspace.hotelManagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
  List<Room> findByHotelId(long hotelId);
}
