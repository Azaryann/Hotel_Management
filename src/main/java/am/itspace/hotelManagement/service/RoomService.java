package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.request.RoomRequest;
import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
  RoomResponse createRoom(RoomRequest roomRequest);
  List<RoomResponse> getAllRooms();
  Optional<RoomResponse> getRoomById(long roomId);
  void deleteRoomById(long roomId);
  Room updateRoom(RoomRequest roomRequest);
}
