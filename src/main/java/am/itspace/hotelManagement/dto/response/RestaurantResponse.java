package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.model.Dish;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantResponse {

  private String name;
  private List<Dish> dishes;

  public RestaurantResponse(String name, List<Dish> dishes) {
    this.name = name;
    this.dishes = dishes;
  }

}
