package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.service.HotelService;
import am.itspace.hotelManagement.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hotels")
public class HotelController {

  private final HotelService hotelService;
  private final RoomService roomService;

  public HotelController(HotelService hotelService, RoomService roomService) {
    this.hotelService = hotelService;
    this.roomService = roomService;
  }

  @GetMapping("/add")
  public String addHotelPage(ModelMap modelMap) {
    List<RoomResponse> rooms = this.roomService.getAllRooms();
    modelMap.put("rooms", rooms);
    return "hotel/addHotel";
  }

  @PostMapping("/add")
  public String addHotel(@ModelAttribute HotelRequest hotelRequest) {
    this.hotelService.addHotel(hotelRequest);
    return "redirect:/hotels";
  }

  @GetMapping
  public String getAllHotel(ModelMap modelMap){
    List<HotelResponse> hotels = this.hotelService.getAllHotels();
    modelMap.put("hotels", hotels);
    return "hotel/hotels";
  }

  @GetMapping("/description")
  public String getRoomById(ModelMap modelMap, @RequestParam("id") int id) {
    Optional<HotelResponse> optionalHotel = this.hotelService.getHotelById(id);
    optionalHotel.ifPresent(hotel -> modelMap.put("hotel", hotel));
    return optionalHotel.isPresent() ? "hotel/hotelById" : "redirect:/";
  }

  @GetMapping("/edit")
  public String updateRoomPage(@RequestParam("id") int id, ModelMap modelMap) {
    Optional<HotelResponse> optionalHotel = this.hotelService.getHotelById(id);
    optionalHotel.ifPresent(hotel -> modelMap.put("hotel", hotel));
    return "hotel/editHotel";
  }

  @PostMapping("/edit")
  public String updateRoom(@ModelAttribute HotelRequest hotelRequest) {
    this.hotelService.updateHotel(hotelRequest);
    return "redirect:/hotels";
  }

  @GetMapping("/delete")
  public String deleteRoom(@RequestParam("id") int id) {
    this.hotelService.deleteHotel(id);
    return "redirect:/hotels";
  }

}
