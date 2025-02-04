package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.request.RestaurantRequest;
import am.itspace.hotelManagement.dto.response.RestaurantResponse;
import am.itspace.hotelManagement.mapper.RestaurantMapper;
import am.itspace.hotelManagement.model.Restaurant;
import am.itspace.hotelManagement.repository.RestaurantRepository;
import am.itspace.hotelManagement.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final RestaurantMapper restaurantMapper;

  public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
    this.restaurantRepository = restaurantRepository;
    this.restaurantMapper = restaurantMapper;
  }

  @Override
  public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest) {

    Restaurant restaurant = new Restaurant(
        restaurantRequest.getName(),
        restaurantRequest.getDishes()
    );

    Restaurant savedRestaurant = this.restaurantRepository.save(restaurant);
    return this.restaurantMapper.mapToRestaurantResponse.apply(savedRestaurant);
  }

  @Override
  public Optional<RestaurantResponse> getRestaurantById(int restaurantId) {
    return Optional.of(this.restaurantRepository.findById(restaurantId)
            .map(restaurant -> this.restaurantMapper.mapToRestaurantResponse.apply(restaurant)))
        .orElseThrow(() -> new RuntimeException("Restaurant not found"));
  }

  @Override
  public List<RestaurantResponse> getAllRestaurant() {
    return this.restaurantRepository.findAll().stream()
        .map(restaurant -> this.restaurantMapper.mapToRestaurantResponse.apply(restaurant))
        .toList();
  }

  @Override
  public void deleteRestaurantById(int restaurantId) {
    this.restaurantRepository.findById(restaurantId)
        .ifPresentOrElse(
            (restaurant) -> this.restaurantRepository.delete(restaurant),
            () -> {
              throw new RuntimeException("Restaurant not found");
            });
  }

  @Override
  public Restaurant updateRestaurant(RestaurantRequest restaurantRequest, int id) {
    return this.restaurantRepository.findById(id)
        .map(restaurant -> {
          restaurant.setName(restaurantRequest.getName());
          restaurant.setDishes(restaurantRequest.getDishes());
          return this.restaurantRepository.save(restaurant);
        })
        .orElseThrow(() -> new RuntimeException("Restaurant not found"));
  }
}
