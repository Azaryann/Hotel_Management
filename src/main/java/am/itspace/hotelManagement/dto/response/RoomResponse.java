package am.itspace.hotelManagement.dto.response;

import am.itspace.hotelManagement.enums.RoomStatus;
import am.itspace.hotelManagement.model.Hotel;
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
  private long id;
  private String type;
  private String  bedType;
  private int roomNumber;
  private double pricePerNight;
  private List<String> imageUrls;
  private RoomStatus roomStatus;
  private Boolean isFreeWiFi;
  private Boolean isSwimmingPool;
  private Boolean isParking;
  private Boolean isFitnessCenter;
  private Hotel hotel;
}
