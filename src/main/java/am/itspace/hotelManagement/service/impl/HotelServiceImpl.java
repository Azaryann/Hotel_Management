package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.dto.response.HotelResponseDto;
import am.itspace.hotelManagement.enums.Rate;
import am.itspace.hotelManagement.mapper.HotelMapper;
import am.itspace.hotelManagement.model.Hotel;
import am.itspace.hotelManagement.repository.HotelRepository;
import am.itspace.hotelManagement.service.HotelService;
import am.itspace.hotelManagement.specification.HotelSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;

  public HotelServiceImpl(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  @Transactional
  public HotelResponse addHotel(HotelRequest hotelRequest) {

    Hotel hotel = Hotel.builder()
        .id(hotelRequest.getId())
        .name(hotelRequest.getName())
        .description(hotelRequest.getDescription())
        .city(hotelRequest.getCity())
        .country(hotelRequest.getCountry())
        .rate(hotelRequest.getRate())
        .build();

    Hotel savedHotel = this.hotelRepository.save(hotel);
    log.info("Hotel created successfully: {}", savedHotel);

    return HotelMapper.mapToHotelResponse.apply(savedHotel);
  }

  @Override
  public Optional<HotelResponseDto> getHotelById(long hotelId) {
    return Optional.of(this.hotelRepository.findById(hotelId)
            .map(hotel -> HotelMapper.mapToHotelResponseDto.apply(hotel)))
        .orElseThrow(() -> new RuntimeException("hotel with id " + hotelId + " not found"));
  }

  @Override
  public Page<HotelResponseDto> getAllHotels(int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Hotel> hotels = this.hotelRepository.findAll(pageable);

    if (hotels.isEmpty()) log.error("hotel not found");

    hotels.forEach(hotel -> hotel.setRooms(hotel.getRooms()));

    List<HotelResponseDto> filteredHotels = hotels.getContent().stream()
        .filter(hotel -> !hotel.getRooms().isEmpty())
        .map(hotel -> HotelMapper.mapToHotelResponseDto.apply(hotel))
        .toList();

    return new PageImpl<>(filteredHotels, pageable, hotels.getTotalElements());
  }

  @Override
  public Page<HotelResponseDto> filterHotel(
      Boolean isFreeWiFi,
      Boolean isSwimmingPool,
      Boolean isParking,
      Boolean isFitnessCenter,
      List<Rate> rates,
      int page,
      int size
  ) {
    Specification<Hotel> specification = Specification.where(null);

    if (Boolean.TRUE.equals(isFreeWiFi)) {
      specification = specification.or(HotelSpecification.hasRoomWithFreeWiFi.apply(true));
    }
    if (Boolean.TRUE.equals(isSwimmingPool)) {
      specification = specification.or(HotelSpecification.hasRoomWithSwimmingPool.apply(true));
    }
    if (Boolean.TRUE.equals(isParking)) {
      specification = specification.or(HotelSpecification.hasRoomWithParking.apply(true));
    }
    if (Boolean.TRUE.equals(isFitnessCenter)) {
      specification = specification.or(HotelSpecification.hasRoomWithFitnessCenter.apply(true));
    }

    if (rates != null && !rates.isEmpty()) {
      List<Rate> rate = rates.stream().toList();
      specification = specification.and(HotelSpecification.hasRate.apply(rate));
    }

    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Hotel> hotels = this.hotelRepository.findAll(specification, pageable);


    List<HotelResponseDto> filteredHotels = hotels.stream()
        .map(hotel -> HotelMapper.mapToHotelResponseDto.apply(hotel))
        .toList();
    return new PageImpl<>(filteredHotels, pageable, hotels.getTotalElements());
  }

  @Override
  public void deleteHotel(long hotelId) {
    this.hotelRepository.findById(hotelId)
        .ifPresentOrElse(hotel -> this.hotelRepository.delete(hotel),
            () -> log.error("hotel not found"));
  }

  @Override
  public Hotel updateHotel(HotelRequest hotelRequest) {
    Hotel hotel = this.hotelRepository.findById(hotelRequest.getId()).orElseThrow();
    hotel.setName(hotel.getName());
    log.info("hotel updated");
    return HotelMapper.mapToEditHotel.apply(hotel);
  }
}
