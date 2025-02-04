package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.request.DishRequest;
import am.itspace.hotelManagement.dto.response.DishResponse;
import am.itspace.hotelManagement.mapper.DishMapper;
import am.itspace.hotelManagement.model.Dish;
import am.itspace.hotelManagement.repository.DishRepository;
import am.itspace.hotelManagement.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public DishServiceImpl(DishRepository dishRepository, DishMapper dishMapper) {
    this.dishRepository = dishRepository;
    this.dishMapper = dishMapper;
  }

  @Override
  public DishResponse createDish(DishRequest dishRequest) {
    Dish dish = new Dish(
        dishRequest.getName(),
        dishRequest.getPrice(),
        dishRequest.getRestaurant()
    );

    Dish savedDish = this.dishRepository.save(dish);

    return this.dishMapper.mapToDishResponse.apply(savedDish);
  }

  @Override
  public Optional<DishResponse> getDishById(int dishId) {
    return Optional.of(this.dishRepository.findById(dishId)
            .map(dish -> this.dishMapper.mapToDishResponse.apply(dish)))
        .orElseThrow(() -> new RuntimeException("Dish not found"));
  }

  @Override
  public List<DishResponse> getAllDishes() {
    return this.dishRepository.findAll().stream()
        .map(dish -> this.dishMapper.mapToDishResponse.apply(dish))
        .toList();
  }

  @Override
  public void deleteDishById(int dishId) {
    this.dishRepository.findById(dishId)
        .ifPresentOrElse(dish -> this.dishRepository.delete(dish),
            () -> {
              throw new RuntimeException("Dish not found");
            });
  }

  @Override
  public Dish updateDishById(DishRequest dishRequest, int dishId) {
    return this.dishRepository.findById(dishId)
        .map(dish -> {
          dish.setName(dishRequest.getName());
          dish.setPrice(dishRequest.getPrice());
          dish.setRestaurant(dishRequest.getRestaurant());

          return this.dishRepository.save(dish);
        })
        .orElseThrow(() -> new RuntimeException("Dish not found"));
  }
}
