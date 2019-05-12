package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.model.Job;
import ua.softserve.ita.model.Skill;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.CVService;
import ua.softserve.ita.service.JobService;
import ua.softserve.ita.service.SkillService;
import ua.softserve.ita.service.pdfcreater.TestCVPDF;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
public class CVController {
    private final CVService cvService;
    private final JobService jobService;
    private final SkillService skillService;
    private final TestCVPDF pdfService;


    @Autowired
    public CVController(CVService cvService, JobService jobService, SkillService skillService, TestCVPDF pdfService) {
        this.cvService = cvService;
        this.jobService = jobService;
        this.skillService = skillService;
        this.pdfService = pdfService;
    }

    @GetMapping(path = {"/cv/{id}"})
    public CV findById(@PathVariable("id") long id) {
        CV cv = cvService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", id)));

        pdfService.createPDF(cv);

        return cv;

    }

    @GetMapping(path = {"/cvs"})
    public List<CV> findAll() {
        return cvService.findAll();
    }

    @PostMapping(path = "/createCV")
    public CV insert(@RequestBody CV cv) {
        Person person = new Person();
        person.setUserId(1L);
        cv.setPerson(person);

        Set<Skill> skills = cv.getSkills();
        Set<Job> jobs = cv.getJobs();
        skills.forEach(x -> x.setCv(cv));
        jobs.forEach(x -> x.setCv(cv));

        cvService.save(cv);
        return cv;
    }

    @PutMapping(path = "/updateCV")
    public CV update(@RequestBody CV cv) {
        Set<Skill> skills = cv.getSkills();
        Set<Job> jobs = cv.getJobs();
        skills.forEach(x -> x.setCv(cv));
        jobs.forEach(x -> x.setCv(cv));


        return cvService.update(cv);
    }

    @DeleteMapping(path = "/deleteCV/{id}")
    public void deleteById(@PathVariable("id") long id) {
        cvService.deleteById(id);
    }

}
