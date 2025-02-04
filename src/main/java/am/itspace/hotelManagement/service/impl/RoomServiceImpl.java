package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.request.RoomRequest;
import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.mapper.RoomMapper;
import am.itspace.hotelManagement.model.Room;
import am.itspace.hotelManagement.repository.RoomRepository;
import am.itspace.hotelManagement.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;

  public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
    this.roomRepository = roomRepository;
    this.roomMapper = roomMapper;
  }

  @Override
  public RoomResponse createRoom(RoomRequest roomRequest) {
    Room room = new Room(roomRequest.getRoomNumber(), roomRequest.getPricePerNight(), roomRequest.getStatus());
    Room savedRoom = this.roomRepository.save(room);
    return this.roomMapper.mapToRoomResponse.apply(savedRoom);
  }

  @Override
  public List<RoomResponse> getAllRooms() {
    return this.roomRepository.findAll().stream()
        .map(room -> this.roomMapper.mapToRoomResponse.apply(room))
        .toList();
  }

  @Override
  public Optional<RoomResponse> getRoomById(int roomId) {
    return Optional.of(this.roomRepository.findById(roomId)
            .map(room -> this.roomMapper.mapToRoomResponse.apply(room)))
        .orElseThrow(() -> new RuntimeException("Room not found"));
  }

  @Override
  public void deleteRoomById(int roomId) {
    this.roomRepository.findById(roomId)
        .ifPresentOrElse(
            (room) -> this.roomRepository.delete(room),
            () -> {
              throw new RuntimeException("Room not found");
            });
  }

  @Override
  public Room updateRoom(RoomRequest roomRequest, int roomId) {
    return this.roomRepository.findById(roomId)
        .map(currentRoom -> {
          currentRoom.setRoomNumber(roomRequest.getRoomNumber());
          currentRoom.setPricePerNight(roomRequest.getPricePerNight());
          currentRoom.setStatus(roomRequest.getStatus());
          return this.roomRepository.save(currentRoom);
        })
        .orElseThrow(() -> new RuntimeException("Room not found"));
  }
}
