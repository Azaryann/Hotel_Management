package am.itspace.hotelManagement.repository;

import am.itspace.hotelManagement.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
