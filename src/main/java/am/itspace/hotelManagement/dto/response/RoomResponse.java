package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

  private int id;
  private String type;
  private String  bedType;
  private int roomNumber;
  private double pricePerNight;
  private List<String> imageUrls;
  private RoomStatus roomStatus;
  private boolean isFreeWiFi;
  private boolean isSwimmingPool;
  private boolean isParking;
  private boolean isFitnessCenter;

}
