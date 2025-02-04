package am.itspace.hotelManagement.mapper;

import am.itspace.hotelManagement.dto.response.RestaurantResponse;
import am.itspace.hotelManagement.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RestaurantMapper {

  public final Function<Restaurant, RestaurantResponse> mapToRestaurantResponse = restaurant -> new RestaurantResponse(
      restaurant.getName(),
      restaurant.getDishes()
  );

}
