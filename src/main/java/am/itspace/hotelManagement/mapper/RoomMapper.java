package am.itspace.hotelManagement.mapper;

import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.model.Room;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoomMapper {

  public final Function<Room, RoomResponse> mapToRoomResponse = room -> new RoomResponse(
      room.getRoomNumber(),
      room.getPricePerNight(),
      room.getStatus()
  );

}
