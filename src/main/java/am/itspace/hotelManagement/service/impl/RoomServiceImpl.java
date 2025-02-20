package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.request.RoomRequest;
import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.mapper.RoomMapper;
import am.itspace.hotelManagement.model.Room;
import am.itspace.hotelManagement.repository.RoomRepository;
import am.itspace.hotelManagement.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static am.itspace.hotelManagement.utils.ImageUtil.generateImages;

@Service
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  @Value("${room.image.upload.path}")
  private String uploadPath;

  public RoomServiceImpl(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @Override
  public RoomResponse createRoom(RoomRequest roomRequest) {
    List<String> imageUrls = generateImages(roomRequest.getImages(), uploadPath);
    Room room = Room.builder()
        .id(roomRequest.getId())
        .type(roomRequest.getType())
        .bedType(roomRequest.getBedType())
        .roomNumber(roomRequest.getRoomNumber())
        .pricePerNight(roomRequest.getPricePerNight())
        .imageUrls(imageUrls)
        .roomStatus(roomRequest.getRoomStatus())
        .isFreeWiFi(roomRequest.getIsFreeWiFi() != null ? roomRequest.getIsFreeWiFi() : false)
        .isSwimmingPool(roomRequest.getIsSwimmingPool() != null ? roomRequest.getIsSwimmingPool() : false)
        .isParking(roomRequest.getIsParking() != null ? roomRequest.getIsParking() : false)
        .isFitnessCenter(roomRequest.getIsFitnessCenter() != null ? roomRequest.getIsFitnessCenter() : false)
        .build();
    Room savedRoom = this.roomRepository.save(room);

    return RoomMapper.mapToRoomResponse.apply(savedRoom);
  }

  @Override
  public List<RoomResponse> getAllRooms() {
    List<Room> rooms = this.roomRepository.findAll();
    if (rooms.isEmpty()) throw new RuntimeException("The list of room does not exist");
    return rooms.stream()
        .map(room -> RoomMapper.mapToRoomResponse.apply(room))
        .toList();
  }

  @Override
  public Optional<RoomResponse> getRoomById(long roomId) {
    return Optional.of(this.roomRepository.findById(roomId)
            .map(room -> RoomMapper.mapToRoomResponse.apply(room)))
        .orElseThrow(() -> new RuntimeException("Room not found"));
  }

  @Override
  public void deleteRoomById(long roomId) {
    this.roomRepository.findById(roomId)
        .ifPresentOrElse(room -> this.roomRepository.delete(room),
            () -> {
              throw new RuntimeException("Room not found");
            });
  }

  @Override
  public Room updateRoom(RoomRequest roomRequest) {
    Room room = this.roomRepository.findById(roomRequest.getId()).orElseThrow();
    room.setRoomNumber(roomRequest.getRoomNumber());
    room.setPricePerNight(roomRequest.getPricePerNight());
    Room updatedRoom = this.roomRepository.save(room);

    return RoomMapper.mapToEditRoomResponse.apply(updatedRoom);
  }
}