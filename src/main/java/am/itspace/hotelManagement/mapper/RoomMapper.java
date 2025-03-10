package am.itspace.hotelManagement.mapper;

import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.entity.Room;

import java.util.function.Function;
import java.util.function.UnaryOperator;


public class RoomMapper {

  private RoomMapper() {}

  public static final Function<Room, RoomResponse> mapToRoomResponse = room -> RoomResponse.builder()
      .id(room.getId())
      .type(room.getType())
      .bedType(room.getBedType())
      .roomNumber(room.getRoomNumber())
      .pricePerNight(room.getPricePerNight())
      .imageUrls(room.getImageUrls())
      .roomStatus(room.getRoomStatus())
      .isFreeWiFi(room.getIsFreeWiFi())
      .isSwimmingPool(room.getIsSwimmingPool())
      .isParking(room.getIsParking())
      .isFitnessCenter(room.getIsFitnessCenter())
      .hotel(room.getHotel())
      .build();



  public static final UnaryOperator<Room> mapToEditRoomResponse = room -> Room.builder()
      .roomNumber(room.getRoomNumber())
      .pricePerNight(room.getPricePerNight())
      .build();

}
