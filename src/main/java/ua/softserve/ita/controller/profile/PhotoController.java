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
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.profile.Photo;
import ua.softserve.ita.service.PhotoService;

import javax.validation.Valid;

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
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get photo by specific id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Photo.class)})
    public Photo findById(@PathVariable("id") Long id) {
        return photoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Photo with id %d was not found?!", id)));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new photo")
    public Photo create(@Valid @RequestBody Photo photo) {
        return photoService.save(photo);
    }

    @PostMapping(path = "/avatars/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Upload photo for person with specific id")
    public Photo uploadAvatar(@Valid @RequestParam("file") MultipartFile file, @PathVariable("user_id") Long userId) {
        return photoService.uploadAvatar(file, userId);
    }

    @PostMapping(path = "/logos/{company_name}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Upload photo for company with specific name")
    public Photo uploadLogo(@Valid @RequestParam("file") MultipartFile file, @PathVariable("company_name") String companyName) {
        return photoService.uploadLogo(file, companyName);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update photo")
    public Photo update(@Valid @RequestBody Photo photo) {
        return photoService.update(photo);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete photo with specific id")
    public void deleteById(@PathVariable("id") Long id) {
        photoService.deleteById(id);
    }

}
