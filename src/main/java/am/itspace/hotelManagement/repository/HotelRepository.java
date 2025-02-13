package am.itspace.hotelManagement.repository;

import am.itspace.hotelManagement.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}
