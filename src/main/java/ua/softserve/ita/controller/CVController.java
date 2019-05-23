package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.service.CVService;
import ua.softserve.ita.service.JobService;
import ua.softserve.ita.service.SkillService;

import java.util.List;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@CrossOrigin
@RestController
@RequestMapping("/resume")
public class CVController {
    private final CVService cvService;
    private final SkillService skillService;
    private final JobService jobService;


    @Autowired
    public CVController(CVService cvService, SkillService skillService, JobService jobService) {
        this.cvService = cvService;
        this.skillService = skillService;
        this.jobService = jobService;
    }

    @GetMapping(path = {"/{id}"})
    public CV findById(@PathVariable("id") long id) {
        return cvService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("CV with id: %d not found", id)));
    }

    @GetMapping(value = "/user")
    public CV getCVByUser() {
        return cvService.findByUserId(getLoggedUser().get().getUserId()).orElseThrow(() -> new ResourceNotFoundException(String.format("CV was not found")));
    }

    @GetMapping(path = {"/all"})
    public List<CV> findAll() {
        return cvService.findAll();
    }

    @PostMapping(path = "/create")
    public CV insert(@RequestBody CV cv) {
        return cvService.save(cv);
    }

    @PutMapping(path = "/update")
    public CV update(@RequestBody CV cv) {
        return cvService.update(cv);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteById(@PathVariable("id") long id) {
        cvService.deleteById(id);
    }

    @DeleteMapping("/skill/{id}")
    public void deleteSkill(@PathVariable("id") Long id) {
        skillService.deleteById(id);
    }

    @DeleteMapping("/job/{id}")
    public void deleteJob(@PathVariable("id") Long id) {
        jobService.deleteById(id);
    }

}
