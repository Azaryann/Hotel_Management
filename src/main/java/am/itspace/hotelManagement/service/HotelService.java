package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HotelService {
  HotelResponse addHotel(HotelRequest hotelRequest);
  Optional<HotelResponse> getHotelById(long hotelId);
  Page<HotelResponse> getAllHotels(int page, int size);
  List<HotelResponse> filterHotel(Boolean isFreeWiFi, Boolean isSwimmingPool, Boolean isParking, Boolean isFitnessCenter);
  void deleteHotel(long hotelId);
  Hotel updateHotel(HotelRequest hotelRequest);
}
