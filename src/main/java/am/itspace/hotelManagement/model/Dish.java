package am.itspace.hotelManagement.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private double price;

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  public Dish(String name, double price, Restaurant restaurant) {
    this.name = name;
    this.price = price;
    this.restaurant = restaurant;
  }
  // TODO Category category
}
