package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.VacancyDTO.VacancyDTO;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.RequirementService;
import ua.softserve.ita.service.VacancyService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;
    private final RequirementService requirementService;
    private final CompanyService companyService;

    @Autowired
    public VacancyController(VacancyService vacancyService, RequirementService requirementService, CompanyService companyService) {
        this.vacancyService = vacancyService;
        this.requirementService = requirementService;
        this.companyService = companyService;
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
        Company company = companyService.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + companyId));
        vacancy.setCompany(company);
        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        vacancyService.save(vacancy);
        requirements.forEach(requirementService::save);
        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public void deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
    }

    @GetMapping("/findAll/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDTO> findAllVacanciesWithPagination(@PathVariable("first") int first) {
        return ResponseEntity.ok().body(vacancyService.findAllVacanciesWithPagination(first));
    }

    @GetMapping("/{companyId}/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDTO> findAllVacanciesByCompanyNameWithPagination(@PathVariable("companyId") Long companyId,
                                                                                  @PathVariable("first") int first) {
        return ResponseEntity.ok().body(vacancyService.findAllVacanciesByCompanyId(companyId, first));
    }

    @GetMapping("hotVacancies/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDTO> findAllHotVacanciesWithPagination(@PathVariable("first") int first) {
        return ResponseEntity.ok().body(vacancyService.findAllHotVacanciesWithPagination(first));
    }


}
