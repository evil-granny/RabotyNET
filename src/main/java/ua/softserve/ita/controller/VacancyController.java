package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.VacancyDto;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Resume;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.ResumeService;
import ua.softserve.ita.service.VacancyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @Autowired
    public VacancyController(VacancyService vacancyService, ResumeService resumeService) {
        this.vacancyService = vacancyService;
        this.resumeService = resumeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancyList = vacancyService.findAll();
        return ResponseEntity.ok().body(vacancyList);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", id)));
        return ResponseEntity.ok().body(vacancy);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public ResponseEntity<Vacancy> updateVacancy(@Valid @RequestBody Vacancy vacancy) {
        final Vacancy updatedVacancy = vacancyService.update(vacancy);
        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/createVacancy/{companyId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy, @PathVariable(value = "companyId") Long companyId) {
        return ResponseEntity.ok(vacancyService.save(vacancy, companyId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public void deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
    }

    @GetMapping("/findAll/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDto> findAllVacanciesWithPagination(@PathVariable("first") int first) {
        return ResponseEntity.ok().body(vacancyService.findAllVacanciesWithPagination(first));
    }

    @GetMapping("/{companyId}/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDto> findAllVacanciesByCompanyNameWithPagination(@PathVariable("companyId") Long companyId,
                                                                                  @PathVariable("first") int first) {
        return ResponseEntity.ok().body(vacancyService.findAllVacanciesByCompanyId(companyId, first));
    }

    @GetMapping("hotVacancies/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDto> findAllHotVacanciesWithPagination(@PathVariable("first") int first) {
        return ResponseEntity.ok().body(vacancyService.findAllHotVacanciesWithPagination(first));
    }

    @GetMapping("closedVacancies/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDto> findAllClosedVacanciesWithPagination(@PathVariable("first") int first) {
        System.out.println(vacancyService.findAllClosedVacanciesWithPagination(first));
        return ResponseEntity.ok().body(vacancyService.findAllClosedVacanciesWithPagination(first));
    }

    @PostMapping("/sendResume/{vacancyId}")
    public ResponseEntity<Resume> sendResumeOnThisVacancy(@Valid @RequestBody Resume resume, @PathVariable("vacancyId") Long vacancyId) {
        return ResponseEntity.ok().body(resumeService.sendResumeOnThisVacancy(resume, vacancyId));
    }

}
