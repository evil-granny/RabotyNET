package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Resume;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.ResumeService;
import ua.softserve.ita.service.JobService;
import ua.softserve.ita.service.SkillService;
import ua.softserve.ita.service.VacancyService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeService resumeService;
    private final SkillService skillService;
    private final JobService jobService;
    private final VacancyService vacancyService;


    @Autowired
    public ResumeController(ResumeService resumeService, SkillService skillService, JobService jobService, VacancyService vacancyService) {
        this.resumeService = resumeService;
        this.skillService = skillService;
        this.jobService = jobService;
        this.vacancyService = vacancyService;
    }

    @GetMapping(path = {"/{id}"})
    public Resume findById(@PathVariable("id") long id) {
        return resumeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resume with id: %d not found", id)));
    }

    @GetMapping(value = "/user")
    public Resume getCVByUser() {
        return resumeService.findByUserId(getLoggedUser().get().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Resume was not found"));
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
    public List<Resume> getResumeByVacancyId(@PathVariable("vacancyId") Long vacancyId){
        return resumeService.findResumeByVacancyId(vacancyId);
    }

}
