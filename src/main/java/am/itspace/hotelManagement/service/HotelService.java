package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
  HotelResponse addHotel(HotelRequest hotelRequest);
  Optional<HotelResponse> getHotelById(int hotelId);
  List<HotelResponse> getAllHotels();
  void deleteHotel(int hotelId);
  Hotel updateHotel(HotelRequest hotelRequest);
}
