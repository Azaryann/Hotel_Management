package am.itspace.hotelManagement.dto.request;

import am.itspace.hotelManagement.model.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishRequest {
  private String name;
  private double price;
  private Restaurant restaurant;
}
