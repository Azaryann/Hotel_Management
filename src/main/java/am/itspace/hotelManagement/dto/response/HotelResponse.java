package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.enums.Rate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

  private long id;
  private String name;
  private String description;
  private String city;
  private String country;
  private Rate rate;
}
