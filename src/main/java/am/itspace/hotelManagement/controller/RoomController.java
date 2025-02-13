package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.request.RoomRequest;
import am.itspace.hotelManagement.dto.response.RoomResponse;
import am.itspace.hotelManagement.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping("/add")
  public String addRoomPage() {
    return "room/addRoom";
  }

  @PostMapping("/add")
  public String addRoom(@ModelAttribute RoomRequest roomRequest) {
    this.roomService.createRoom(roomRequest);
    return "redirect:/rooms";
  }

  @GetMapping
  public String getAllRooms(ModelMap modelMap){
    List<RoomResponse> rooms = this.roomService.getAllRooms();
    modelMap.put("rooms", rooms);
    return "room/rooms";
  }


  // Admin And Customer
  @GetMapping("/description")
  public String getRoomById(ModelMap modelMap, @RequestParam("id") int id) {
    Optional<RoomResponse> optionalRoomResponse = this.roomService.getRoomById(id);
    optionalRoomResponse.ifPresent(room -> modelMap.put("room", room));
    return optionalRoomResponse.isPresent() ? "room/roomById" : "redirect:/";
  }

  @GetMapping("/edit")
  public String updateRoomPage(@RequestParam("id") int id, ModelMap modelMap) {
    Optional<RoomResponse> roomResponse = this.roomService.getRoomById(id);
    roomResponse.ifPresent(room -> modelMap.put("room", room));
    return "room/editRoom";
  }

  @PostMapping("/edit")
  public String updateRoom(@ModelAttribute RoomRequest room) {
    this.roomService.updateRoom(room);
    return "redirect:/rooms";
  }

  @GetMapping("/delete")
  public String deleteRoom(@RequestParam("id") int id) {
    this.roomService.deleteRoomById(id);
    return "redirect:/rooms";
  }

}
