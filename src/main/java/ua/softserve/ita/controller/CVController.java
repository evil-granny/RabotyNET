package ua.softserve.ita.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;
@CrossOrigin
@RestController
public class CVController {
    
    @Resource(name = "cvService")
    private Service<CV> cvService;

    @GetMapping(path = {"/cv/{id}"})
    public CV findById(@PathVariable("id") long id) {
        return cvService.findById(id);
    }

    @GetMapping(path = {"/cvs"})
    public List<CV> findAll() {
        return cvService.findAll();
    }

    @PostMapping(path = "/createCV")
    public CV insert(@RequestBody CV cv) {
        return cvService.insert(cv);
    }

    @PutMapping(path = "/updateCV/{id}")
    public CV update(@RequestBody CV cv, @PathVariable("id") long id) {
        return cvService.update(cv, id);
    }

    @DeleteMapping(path = "/deleteCV/{id}")
    public void deleteById(@PathVariable("id") long id) {
        cvService.deleteById(id);
    }

}

