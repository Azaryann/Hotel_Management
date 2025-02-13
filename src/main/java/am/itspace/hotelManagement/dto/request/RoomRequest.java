package am.itspace.hotelManagement.dto.request;

import am.itspace.hotelManagement.enums.RoomStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class RoomRequest {
  private int id;
  private String type;
  private String  bedType;
  private int roomNumber;
  private double pricePerNight;
  private RoomStatus roomStatus;
  private List<MultipartFile> images;
  private Boolean isFreeWiFi;
  private Boolean isSwimmingPool;
  private Boolean isParking;
  private Boolean isFitnessCenter;
}
