package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.request.RoomRequest;
import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.mapper.RoomMapper;
import am.itspace.hotelManagement.model.Hotel;
import am.itspace.hotelManagement.model.Room;
import am.itspace.hotelManagement.repository.HotelRepository;
import am.itspace.hotelManagement.repository.RoomRepository;
import am.itspace.hotelManagement.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static am.itspace.hotelManagement.utils.ImageUtil.generateMultiImages;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final HotelRepository hotelRepository;

  @Value("${room.image.upload.path}")
  private String uploadPath;

  public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {
    this.roomRepository = roomRepository;
    this.hotelRepository = hotelRepository;
  }

  @Override
  public RoomResponse createRoom(RoomRequest roomRequest) {
    List<String> imageUrls = generateMultiImages(roomRequest.getImages(), uploadPath);
    Optional<Hotel> optionalHotel = hotelRepository.findById(roomRequest.getHotelId());

    if (!optionalHotel.isPresent()) {
      log.error("Hotel with id {} not found", roomRequest.getHotelId());
    }

    Hotel hotel = optionalHotel.get();

    Room room = Room.builder()
        .id(roomRequest.getId())
        .type(roomRequest.getType())
        .bedType(roomRequest.getBedType())
        .roomNumber(roomRequest.getRoomNumber())
        .pricePerNight(roomRequest.getPricePerNight())
        .imageUrls(imageUrls)
        .roomStatus(roomRequest.getRoomStatus())
        .isFreeWiFi(roomRequest.getIsFreeWiFi() != null && roomRequest.getIsFreeWiFi())
        .isSwimmingPool(roomRequest.getIsSwimmingPool() != null && roomRequest.getIsSwimmingPool())
        .isParking(roomRequest.getIsParking() != null && roomRequest.getIsParking())
        .isFitnessCenter(roomRequest.getIsFitnessCenter() != null && roomRequest.getIsFitnessCenter())
        .hotel(hotel)
        .build();

    Room savedRoom = this.roomRepository.save(room);
    log.info("Room created: {}", savedRoom);

    return RoomMapper.mapToRoomResponse.apply(savedRoom);
  }

  @Override
  public List<RoomResponse> getAllRooms() {
    List<Room> rooms = this.roomRepository.findAll();
    if (rooms.isEmpty()) log.error("No rooms found");
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
              log.error("Room with id {} not found", roomId);
            });
  }

  @Override
  public Room updateRoom(RoomRequest roomRequest) {
    Room room = this.roomRepository.findById(roomRequest.getId()).orElseThrow();
    room.setRoomNumber(roomRequest.getRoomNumber());
    room.setPricePerNight(roomRequest.getPricePerNight());
    Room updatedRoom = this.roomRepository.save(room);
    log.info("Room updated: {}", updatedRoom);

    return RoomMapper.mapToEditRoomResponse.apply(updatedRoom);
  }
}