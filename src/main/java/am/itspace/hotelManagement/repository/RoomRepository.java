package am.itspace.hotelManagement.repository;

import am.itspace.hotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
