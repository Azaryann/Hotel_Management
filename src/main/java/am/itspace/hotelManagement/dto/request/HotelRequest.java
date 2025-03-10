package am.itspace.hotelManagement.dto.request;

import am.itspace.hotelManagement.enums.Rate;
import lombok.Data;

@Data
public class HotelRequest {
  private long id;
  private String name;
  private String description;
  private String city;
  private String country;
  private Rate rate;
}
