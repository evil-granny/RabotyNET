package ua.softserve.ita.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.service.profile.PhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
public class PhotoController {

    //private static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    private static String uploadDirectory = "C:\\Users\\DeH4uK\\Desktop\\Photo";

    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file) {
        System.out.println("[upload]");
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*@Autowired
    PhotoService photoUploadService;

    @PostMapping()
    public void handlePhotoUpload(@RequestParam("file") MultipartFile photo) {
        System.out.println("[uploadPhoto]");
        photoUploadService.store(photo);
    }

    @GetMapping()
    public ResponseEntity<Resource> getPhoto(@PathVariable String fileName) {
        Resource photo = photoUploadService.loadPhoto(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + photo.getFilename() + "\"")
                .body(photo);
    }*/

}
