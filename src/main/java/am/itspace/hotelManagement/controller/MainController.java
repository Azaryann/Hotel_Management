package am.itspace.hotelManagement.controller;

import org.springframework.beans.factory.annotation.Value;
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

@Controller
@RequestMapping("/")
public class MainController {

  @Value("${room.image.upload.path}")
  private String uploadPath;


  @GetMapping
  public String mainPage(ModelMap modelMap) {
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
