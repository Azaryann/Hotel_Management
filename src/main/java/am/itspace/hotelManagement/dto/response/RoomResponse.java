package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomResponse {

  private int roomNumber;
  private double pricePerNight;
  private Status status;

  public RoomResponse(int roomNumber, double pricePerNight, Status status) {
    this.roomNumber = roomNumber;
    this.pricePerNight = pricePerNight;
    this.status = status;
  }

}
