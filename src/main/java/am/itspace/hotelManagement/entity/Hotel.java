package am.itspace.hotelManagement.entity;

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
  private long id;
  private String name;
  private String description;
  private String city;
  private String country;

  @Enumerated(EnumType.STRING)
  private Rate rate;

  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Room> rooms;
}
