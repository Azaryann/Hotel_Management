package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.mapper.HotelMapper;
import am.itspace.hotelManagement.model.Hotel;
import am.itspace.hotelManagement.model.Room;
import am.itspace.hotelManagement.repository.HotelRepository;
import am.itspace.hotelManagement.repository.RoomRepository;
import am.itspace.hotelManagement.service.HotelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final RoomRepository roomRepository;
  @Value("${room.image.upload.path}")
  private String uploadPath;

  public HotelServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository) {
    this.hotelRepository = hotelRepository;
    this.roomRepository = roomRepository;
  }

  @Override
  public HotelResponse addHotel(HotelRequest hotelRequest) {
    Iterable<Integer> iterateRooms = hotelRequest.getRooms();
    List<Room> selectedRooms = this.roomRepository.findAllById(iterateRooms);
    Hotel hotel = Hotel.builder()
        .id(hotelRequest.getId())
        .name(hotelRequest.getName())
        .description(hotelRequest.getDescription())
        .city(hotelRequest.getCity())
        .country(hotelRequest.getCountry())
        .longitude(hotelRequest.getLongitude())
        .latitude(hotelRequest.getLatitude())
        .rate(hotelRequest.getRate())
        .rooms(selectedRooms)
        .build();
    selectedRooms.forEach(room -> room.setHotel(hotel));
    Hotel savedHotel = this.hotelRepository.save(hotel);
    return HotelMapper.mapToHotelResponse.apply(savedHotel);
  }

  @Override
  public Optional<HotelResponse> getHotelById(int hotelId) {
    return Optional.of(this.hotelRepository.findById(hotelId)
            .map(hotel -> HotelMapper.mapToHotelResponse.apply(hotel)))
        .orElseThrow(() -> new RuntimeException("hotel not found"));
  }

  @Override
  public List<HotelResponse> getAllHotels() {
    List<Hotel> hotels = this.hotelRepository.findAll();

    if (hotels.isEmpty()) throw new RuntimeException("The list of hotel does not exist");

    return hotels.stream()
        .map(hotel -> HotelMapper.mapToHotelResponse.apply(hotel))
        .toList();
  }

  @Override
  public void deleteHotel(int hotelId) {
    this.hotelRepository.findById(hotelId)
        .ifPresentOrElse(hotel -> this.hotelRepository.delete(hotel),
            () -> {
              throw new RuntimeException("hotel not found");
            });
  }

  @Override
  public Hotel updateHotel(HotelRequest hotelRequest) {
    Hotel hotel = this.hotelRepository.findById(hotelRequest.getId()).orElseThrow();
    hotel.setName(hotel.getName());
    return HotelMapper.mapToEditHotel.apply(hotel);
  }
}
