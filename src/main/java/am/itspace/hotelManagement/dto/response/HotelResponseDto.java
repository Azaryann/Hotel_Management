package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.enums.Rate;
import am.itspace.hotelManagement.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDto {

  private long id;
  private String name;
  private String description;
  private String city;
  private String country;
  private Rate rate;
  private List<Room> rooms;
}
