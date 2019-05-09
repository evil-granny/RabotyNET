package ua.softserve.ita.controller.profile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.model.profile.Photo;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@CrossOrigin
@RestController
public class PhotoController {

    private static final String uploadDirectory = System.getProperty("user.dir") + "\\uploads";

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource(name = "photoService")
    private Service<Photo> photoService;

    @Resource(name = "personService")
    private Service<Person> personService;

    @RequestMapping(value = "/uploadPhoto/{userId}", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file, @PathVariable("userId") Long userId) {
        try {
            Files.createDirectory(Paths.get(uploadDirectory));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

        Path path = Paths.get(uploadDirectory, file.getOriginalFilename());

        try {
            Files.write(path, file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Photo photo = new Photo();
        photo.setName(file.getOriginalFilename());
        photoService.create(photo);

        Person person = personService.findById(userId);
        person.setPhoto(photo);
        personService.update(person);
    }

    @RequestMapping(value = "/loadPhotoAsByteArray/{fileName}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] loadAsByteArray(@PathVariable("fileName") String fileName) throws IOException {
        Path photo = Paths.get(uploadDirectory).resolve(fileName + ".jpg");

        InputStream inputStream = new FileInputStream(photo.toFile());

        return IOUtils.toByteArray(inputStream);
    }

    @RequestMapping(value = "/loadAsPhotoObject/{id}", method = RequestMethod.GET)
    public Photo loadAsPhotoObject(@PathVariable("id") Long id) throws IOException {
        Photo photo = photoService.findById(id);

        Path file = Paths.get(uploadDirectory).resolve(photo.getName());

        byte[] fileContent = FileUtils.readFileToByteArray(file.toFile());
        photo.setName(Base64.getEncoder().encodeToString(fileContent));

        return photo;
    }

}
