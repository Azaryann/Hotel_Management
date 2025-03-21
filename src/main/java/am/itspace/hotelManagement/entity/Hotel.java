package am.itspace.hotelManagement.entity;

import am.itspace.hotelManagement.enums.Rate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
