package am.itspace.hotelManagement.mapper;

import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.dto.response.HotelResponseDto;
import am.itspace.hotelManagement.model.Hotel;

import java.util.function.Function;
import java.util.function.UnaryOperator;


public class HotelMapper {

  private HotelMapper(){}

  public static final Function<Hotel, HotelResponse> mapToHotelResponse = hotel -> HotelResponse.builder()
      .id(hotel.getId())
      .name(hotel.getName())
      .description(hotel.getDescription())
      .city(hotel.getCity())
      .country(hotel.getCountry())
      .rate(hotel.getRate())
      .build();

  public static final Function<Hotel, HotelResponseDto> mapToHotelResponseDto = hotel -> HotelResponseDto.builder()
      .id(hotel.getId())
      .name(hotel.getName())
      .description(hotel.getDescription())
      .city(hotel.getCity())
      .country(hotel.getCountry())
      .rate(hotel.getRate())
      .rooms(hotel.getRooms())
      .build();


  public static final UnaryOperator<Hotel> mapToEditHotel = hotel -> Hotel.builder()
      .name(hotel.getName())
      .build();

}
