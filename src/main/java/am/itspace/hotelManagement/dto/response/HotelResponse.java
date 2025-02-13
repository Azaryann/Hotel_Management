package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.enums.Rate;
import am.itspace.hotelManagement.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

  private int id;
  private String name;
  private String description;
  private String city;
  private String country;
  private double longitude;
  private double latitude;
  private Rate rate;
  private List<Room> rooms;

}
