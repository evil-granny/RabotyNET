package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.model.Job;
import ua.softserve.ita.model.Skill;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
public class CVController {
    
    @Resource(name = "cvService")
    private Service<CV> cvService;

    @Resource(name = "jobService")
    private Service<Job> jobService;

    @Resource(name = "skillService")
    private Service<Skill> skillService;

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
        Set<Skill> skills = cv.getSkills();
        skills.forEach(x -> x.setCv(cv));
        cvService.insert(cv);
        skills.forEach(x -> skillService.insert(x));
        Set<Job> jobs = cv.getJobs();
        jobs.forEach(x -> x.setCv(cv));
        jobs.forEach(x -> jobService.insert(x));
        return cv;
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

