package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.request.DishRequest;
import am.itspace.hotelManagement.dto.response.DishResponse;
import am.itspace.hotelManagement.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {

  DishResponse createDish(DishRequest dishRequest);
  Optional<DishResponse> getDishById(int dishId);
  List<DishResponse> getAllDishes();
  void deleteDishById(int dishId);
  Dish updateDishById(DishRequest dishRequest, int dishId);

}
