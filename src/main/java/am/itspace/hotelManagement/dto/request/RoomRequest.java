package am.itspace.hotelManagement.dto.request;

import am.itspace.hotelManagement.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomRequest {

  private int roomNumber;
  private double pricePerNight;
  private Status status;
}
