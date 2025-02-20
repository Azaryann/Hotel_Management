package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.response.HotelResponse;
import am.itspace.hotelManagement.service.HotelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class MainController {

  private final HotelService hotelService;

  @Value("${room.image.upload.path}")
  private String uploadPath;

  public MainController(HotelService hotelService) {
    this.hotelService = hotelService;
  }


  @GetMapping
  public String mainPage(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "5") int pageSize, ModelMap modelMap) {
    Page<HotelResponse> hotels = this.hotelService.getAllHotels(pageNumber, pageSize);
    int totalPage = hotels.getTotalPages();
    if (totalPage > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
          .boxed()
          .toList();
      modelMap.put("pageNumbers", pageNumbers);
    }
    modelMap.put("hotels", hotels);
    return "index";
  }

  @GetMapping(value = "/get-image", produces = MediaType.IMAGE_JPEG_VALUE)
  public @ResponseBody byte[] getImage(@RequestParam("img") String imageUrl) throws IOException {
    File file = new File(uploadPath, imageUrl);
    if (file.exists()) {
      try (InputStream inputStream = new FileInputStream(file);
           ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
      }
    }
    return new byte[0];
  }

}
