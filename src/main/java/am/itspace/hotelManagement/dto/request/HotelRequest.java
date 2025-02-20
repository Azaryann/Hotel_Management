package am.itspace.hotelManagement.dto.request;

import am.itspace.hotelManagement.enums.Rate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {
  private long id;
  private String name;
  private String description;
  private String city;
  private String country;
  private double longitude;
  private double latitude;
  private Rate rate;
  private List<Long> rooms;
}
