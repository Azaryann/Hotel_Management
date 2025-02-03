package am.itspace.hotelManagement.repository;

import am.itspace.hotelManagement.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
