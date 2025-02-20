package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.service.HotelService;
import am.itspace.hotelManagement.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

//  @GetMapping
//  public String getAllHotel(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "5") int pageSize, ModelMap modelMap){
//    Page<HotelResponse> hotels = this.hotelService.getAllHotels(pageNumber, pageSize);
//    int totalPage = hotels.getTotalPages();
//    if (totalPage > 0) {
//      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
//          .boxed()
//          .toList();
//      modelMap.put("pageNumbers", pageNumbers);
//    }
//    modelMap.put("hotels", hotels);
//    return "hotel/hotels";
//  }

  @GetMapping("/description")
  public String getRoomById(ModelMap modelMap, @RequestParam("id") long id) {
    Optional<HotelResponse> optionalHotel = this.hotelService.getHotelById(id);
    optionalHotel.ifPresent(hotel -> modelMap.put("hotel", hotel));
    return optionalHotel.isPresent() ? "hotel/hotelById" : "redirect:/";
  }

  @GetMapping("/edit")
  public String updateRoomPage(@RequestParam("id") long id, ModelMap modelMap) {
    Optional<HotelResponse> optionalHotel = this.hotelService.getHotelById(id);
    optionalHotel.ifPresent(hotel -> modelMap.put("hotel", hotel));
    return "hotel/editHotel";
  }

  @GetMapping("/filter")
  public String filterHotel(
      ModelMap modelMap,
      @RequestParam(required = false) Boolean isFreeWiFi,
      @RequestParam(required = false) Boolean isSwimmingPool,
      @RequestParam(required = false) Boolean isParking,
      @RequestParam(required = false) Boolean isFitnessCenter
  ) {
    List<HotelResponse> hotels = this.hotelService.filterHotel(isFreeWiFi, isSwimmingPool, isParking, isFitnessCenter);
    modelMap.put("hotels", hotels);
    return "hotel/filter";
  }

  @PostMapping("/edit")
  public String updateRoom(@ModelAttribute HotelRequest hotelRequest) {
    this.hotelService.updateHotel(hotelRequest);
    return "redirect:/hotels";
  }

  @GetMapping("/delete")
  public String deleteRoom(@RequestParam("id") long id) {
    this.hotelService.deleteHotel(id);
    return "redirect:/hotels";
  }

}
