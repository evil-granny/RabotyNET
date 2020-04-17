package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.Resume;
import ua.com.service.ResumeService;
import ua.com.service.JobService;
import ua.com.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final SkillService skillService;
    private final JobService jobService;

    @Autowired
    public ResumeController(ResumeService resumeService, SkillService skillService, JobService jobService) {
        this.resumeService = resumeService;
        this.skillService = skillService;
        this.jobService = jobService;
    }

    @GetMapping(path = {"/{id}"})
    public Resume findById(@PathVariable("id") long id) {
        return resumeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resume with id: %d not found", id)));
    }

    @GetMapping(value = "/user/{userId}")
    public Resume getResumeByUserId(@PathVariable("userId") long userId) {
        return resumeService.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resume was not found with user id: %d", userId)));
    }

    @GetMapping(value = "/exists/{userId}")
    public boolean exists(@PathVariable("userId") Long userId) {
        return resumeService.findByUserId(userId).isPresent();
    }

    @GetMapping(path = {"/all"})
    public List<Resume> findAll() {
        return resumeService.findAll();
    }

    @PostMapping(path = "/create")
    public Resume insert(@RequestBody Resume resume) {
        return resumeService.save(resume);
    }

    @PutMapping(path = "/update")
    public Resume update(@RequestBody Resume resume) {
        return resumeService.update(resume);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteById(@PathVariable("id") long id) {
        resumeService.deleteById(id);
    }

    @DeleteMapping("/skill/{id}")
    public void deleteSkill(@PathVariable("id") Long id) {
        skillService.deleteById(id);
    }

    @DeleteMapping("/job/{id}")
    public void deleteJob(@PathVariable("id") Long id) {
        jobService.deleteById(id);
    }

    @GetMapping("/byVacancyId/{vacancyId}")
    public List<Resume> getResumeByVacancyId(@PathVariable("vacancyId") Long vacancyId) {
        return resumeService.findResumeByVacancyId(vacancyId);
    }

    @GetMapping(value = "/existsResume/{userId}")
    public boolean existsResume(@PathVariable("userId") Long userId) {
        return resumeService.existsResume(userId);
    }

}
