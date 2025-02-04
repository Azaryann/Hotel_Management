package am.itspace.hotelManagement.dto.request;

import am.itspace.hotelManagement.model.Dish;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantRequest {

  private String name;
  private List<Dish> dishes;

}
