package am.itspace.hotelManagement.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class ImageUtil {

  public static List<String> generateImages(List<MultipartFile> multipartFiles, String uploadPath) {
    List<String> imageUrls = new ArrayList<>();
    for (MultipartFile image : multipartFiles) {
      String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
      Path filePath = Paths.get(uploadPath + filename);
      try (InputStream inputStream = image.getInputStream();
           BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
           OutputStream outputStream = Files.newOutputStream(filePath);
           BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
        byte[] buffer = new byte[8192];
        int readByte;
        while ((readByte = bufferedInputStream.read(buffer)) != -1) {
          bufferedOutputStream.write(buffer, 0, readByte);
        }
        imageUrls.add(filename);
      } catch (IOException e) {
        throw new RuntimeException("Image not found");
      }
    }
    return imageUrls;
  }

}
