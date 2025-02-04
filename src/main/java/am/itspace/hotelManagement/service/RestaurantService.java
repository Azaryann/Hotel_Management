package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.request.RestaurantRequest;
import am.itspace.hotelManagement.dto.response.RestaurantResponse;
import am.itspace.hotelManagement.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
  RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest);
  Optional<RestaurantResponse> getRestaurantById(int restaurantId);
  List<RestaurantResponse> getAllRestaurant();
  void deleteRestaurantById(int restaurantId);
  Restaurant updateRestaurant(RestaurantRequest restaurantRequest, int id);
}
