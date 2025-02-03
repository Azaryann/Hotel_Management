package am.itspace.hotelManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


  @GetMapping("/")
  public String mainPage() {
    return "index";
  }

  @GetMapping("/facilities")
  public String openFacility() {
    return "facilities";
  }


  @GetMapping("/contactUs")
  public String openContactUs() {
    return "contact-page";
  }


  @GetMapping("/roomsAndSuites")
  public String openRoomsAndSuites() {
    return "rooms-and-suites";
  }

}
