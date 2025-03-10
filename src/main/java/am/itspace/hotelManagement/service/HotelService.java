package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.dto.response.HotelResponseDto;
import am.itspace.hotelManagement.enums.Rate;
import am.itspace.hotelManagement.entity.Hotel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface HotelService {
  HotelResponse addHotel(HotelRequest hotelRequest);
  Optional<HotelResponseDto> getHotelById(long hotelId);
  Page<HotelResponseDto> getAllHotels(int page, int size);
  Page<HotelResponseDto> filterHotel(
      Boolean isFreeWiFi,
      Boolean isSwimmingPool,
      Boolean isParking,
      Boolean isFitnessCenter,
      List<Rate> rate,
      int page,
      int size
  );
  void deleteHotel(long hotelId);
  Hotel updateHotel(HotelRequest hotelRequest);
}
