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
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  // Admin
  @GetMapping("/rooms/add")
  public String addRoomPage() {
    return "room/addRoom";
  }
  // Admin
  @PostMapping("/rooms/add")
  public String addRoom(@ModelAttribute RoomRequest roomRequest) {
    this.roomService.createRoom(roomRequest);
    return "redirect:/";
  }

  // Admin And Customer
  @GetMapping("/rooms")
  public String getAllRooms(ModelMap modelMap){
    List<RoomResponse> rooms = this.roomService.getAllRooms();
    modelMap.put("rooms", rooms);
    return "room/rooms";
  }

  // Admin And Customer
  @GetMapping("/rooms/description")
  public String getRoomById(ModelMap modelMap, @RequestParam("id") long id) {
    Optional<RoomResponse> optionalRoomResponse = this.roomService.getRoomById(id);
    optionalRoomResponse.ifPresent(room -> modelMap.put("room", room));
    return optionalRoomResponse.isPresent() ? "room/roomById" : "redirect:/";
  }

  // Admin
  @GetMapping("/rooms/edit")
  public String updateRoomPage(@RequestParam("id") long id, ModelMap modelMap) {
    Optional<RoomResponse> roomResponse = this.roomService.getRoomById(id);
    roomResponse.ifPresent(room -> modelMap.put("room", room));
    return "room/editRoom";
  }

  // Admin
  @PostMapping("/rooms/edit")
  public String updateRoom(@ModelAttribute RoomRequest room) {
    this.roomService.updateRoom(room);
    return "redirect:/rooms";
  }

  // Admin
  @GetMapping("/rooms/delete")
  public String deleteRoom(@RequestParam("id") long id) {
    this.roomService.deleteRoomById(id);
    return "redirect:/rooms";
  }

}