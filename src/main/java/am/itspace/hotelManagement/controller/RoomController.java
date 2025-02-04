package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.request.RoomRequest;
import am.itspace.hotelManagement.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping
  public String getRoomPage() {
    return "room/rooms";
  }

  @PostMapping("/add")
  public String addRoomPage(@ModelAttribute RoomRequest roomRequest) {
    this.roomService.createRoom(roomRequest);
    return "room/addRoom";
  }
}
