package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.request.HotelRequest;
import am.itspace.hotelManagement.dto.response.HotelResponseDto;
import am.itspace.hotelManagement.service.HotelService;
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

  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping("/add")
  public String addHotelPage() {
    return "hotel/addHotel";
  }

  @PostMapping("/add")
  public String addHotel(@ModelAttribute HotelRequest hotelRequest) {
    this.hotelService.addHotel(hotelRequest);
    return "redirect:/";
  }

  @GetMapping("/description")
  public String getRoomById(ModelMap modelMap, @RequestParam("id") long id) {
    Optional<HotelResponseDto> optionalHotel = this.hotelService.getHotelById(id);
    optionalHotel.ifPresent(hotel -> modelMap.put("hotel", hotel));
    return optionalHotel.isPresent() ? "hotel/hotelById" : "redirect:/";
  }

  @GetMapping("/edit")
  public String updateRoomPage(@RequestParam("id") long id, ModelMap modelMap) {
    Optional<HotelResponseDto> optionalHotel = this.hotelService.getHotelById(id);
    optionalHotel.ifPresent(hotel -> modelMap.put("hotel", hotel));
    return "hotel/editHotel";
  }

  @GetMapping("/filter")
  public String filterHotel(
      ModelMap modelMap,
      @RequestParam(required = false) Boolean isFreeWiFi,
      @RequestParam(required = false) Boolean isSwimmingPool,
      @RequestParam(required = false) Boolean isParking,
      @RequestParam(required = false) Boolean isFitnessCenter,
      @RequestParam(defaultValue = "1") int pageNumber,
      @RequestParam(defaultValue = "3") int pageSize
      ) {
    Page<HotelResponseDto> hotels = this.hotelService
        .filterHotel(isFreeWiFi, isSwimmingPool, isParking, isFitnessCenter, pageNumber, pageSize);

    int totalPage = hotels.getTotalPages();
    if (totalPage > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
          .boxed()
          .toList();
      modelMap.put("pageNumbers", pageNumbers);
    }

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
    return "redirect:/";
  }

}