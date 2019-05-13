package ua.softserve.ita.controller.profile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.model.profile.Photo;
import ua.softserve.ita.service.PhotoService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/photo")
@Api(value = "PhotoControllerAPI", produces = MediaType.IMAGE_JPEG_VALUE)
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(path = {"/{id}"})
    @ApiOperation(value = "Get photo by specific id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Photo.class)})
    public Optional<Photo> findById(@PathVariable("id") Long id) {
        return photoService.findById(id);
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation(value = "Load photo by specific name")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = byte[].class)})
    public @ResponseBody
    byte[] loadAsByteArray(@RequestParam("name") String name) throws IOException {
        return photoService.loadAsByteArray(name);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new photo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Photo.class)})
    public Photo create(@Valid @RequestBody Photo photo) {
        return photoService.save(photo);
    }

    @PostMapping(path = "/{user_id}")
    @ApiOperation(value = "Upload photo for user with specific id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public Photo upload(@RequestParam("file") MultipartFile file, @PathVariable("user_id") Long userId) throws IOException {
        return photoService.upload(file, userId);
    }

    @PutMapping()
    @ApiOperation(value = "Update photo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Photo.class)})
    public Photo update(@Valid @RequestBody Photo photo) {
        return photoService.update(photo);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete the photo with specific id")
    public void deleteById(@PathVariable("id") Long id) {
        photoService.deleteById(id);
    }

}
