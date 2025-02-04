package am.itspace.hotelManagement.mapper;

import am.itspace.hotelManagement.dto.response.DishResponse;
import am.itspace.hotelManagement.model.Dish;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DishMapper {

  public final Function<Dish, DishResponse> mapToDishResponse = dish -> new DishResponse(
      dish.getName(),
      dish.getPrice(),
      dish.getRestaurant().getId()
  );

}
