package ua.softserve.ita.controller.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@CrossOrigin
@RestController
public class PhotoController {

    //private static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static Path uploadDirectory = Paths.get("C:\\Users\\DeH4uK\\Desktop\\Photo");

    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file) {
        System.out.println("[upload]");
        Path path = Paths.get(uploadDirectory.toString(), file.getOriginalFilename());
        try {
            Files.write(path, file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/loadPhoto/{fileName}", method = RequestMethod.GET)
    public Optional<Resource> load(@PathVariable("fileName") String fileName) {
        System.out.println("[load]");
        try {
            Path photo = uploadDirectory.resolve(fileName);
            Resource resource = new UrlResource(photo.toUri());
            if (resource.exists() || resource.isReadable()) {
                return Optional.of(resource);
            }
        } catch (MalformedURLException ex) {
            logger.error(ex.getMessage());
        }

        return Optional.empty();
    }

    public void init() {
        try {
            Files.createDirectory(uploadDirectory);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

}
