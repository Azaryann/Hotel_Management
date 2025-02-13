package am.itspace.hotelManagement.model;

import am.itspace.hotelManagement.enums.Rate;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  private String city;
  private String country;
  private double longitude;
  private double latitude;

  @Enumerated(EnumType.STRING)
  private Rate rate;

  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Room> rooms;
}
