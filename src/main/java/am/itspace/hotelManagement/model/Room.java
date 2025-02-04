package am.itspace.hotelManagement.model;

import am.itspace.hotelManagement.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int roomNumber;
  private double pricePerNight;
  // TODO String type
  private Status status;

  public Room(int roomNumber, double pricePerNight, Status status) {
    this.roomNumber = roomNumber;
    this.pricePerNight = pricePerNight;
    this.status = status;
  }

}
