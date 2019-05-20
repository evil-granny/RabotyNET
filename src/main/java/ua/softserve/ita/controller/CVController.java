package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.service.CVService;

import java.util.List;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@CrossOrigin
@RestController
public class CVController {
    private final CVService cvService;


    @Autowired
    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    @GetMapping(path = {"/cv/{id}"})
    public CV findById(@PathVariable("id") long id) {
        CV cv = cvService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("CV with id: %d not found", id)));
        return cv;
    }

    @GetMapping(value = "/user/cv/{id}")
    public CV getCVByUser(@PathVariable("id") long id) {
        return cvService.findByUserId(getLoggedUser().get().getUserId());
    }

    @GetMapping(path = {"/cvs"})
    public List<CV> findAll() {
        return cvService.findAll();
    }

    @PostMapping(path = "/createCV")
    public CV insert(@RequestBody CV cv) {
        return cvService.save(cv);
    }

    @PutMapping(path = "/updateCV")
    public CV update(@RequestBody CV cv) {
        return cvService.update(cv);
    }

    @DeleteMapping(path = "/deleteCV/{id}")
    public void deleteById(@PathVariable("id") long id) {
        cvService.deleteById(id);
    }

}
