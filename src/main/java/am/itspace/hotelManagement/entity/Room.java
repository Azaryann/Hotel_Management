package am.itspace.hotelManagement.entity;

import am.itspace.hotelManagement.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String type;
  private String bedType;
  private int roomNumber;
  private double pricePerNight;
  
  @ElementCollection
  private List<String> imageUrls;

  @Enumerated(EnumType.STRING)
  private RoomStatus roomStatus;
  private Boolean isFreeWiFi;
  private Boolean isSwimmingPool;
  private Boolean isParking;
  private Boolean isFitnessCenter;

  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;
}
