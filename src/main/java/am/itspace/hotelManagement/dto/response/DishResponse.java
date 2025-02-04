package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.model.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishResponse {
  private String name;
  private double price;
  private int restaurantId;

  public DishResponse(String name, double price, int restaurantId) {
    this.name = name;
    this.price = price;
    this.restaurantId = restaurantId;
  }

}
