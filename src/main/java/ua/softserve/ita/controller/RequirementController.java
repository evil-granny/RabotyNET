package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.RequirementService;
import ua.softserve.ita.service.VacancyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requirements")
public class RequirementController {

    private final RequirementService requirementService;
    private final VacancyService vacancyService;

    @Autowired
    public RequirementController(RequirementService requirementService, VacancyService vacancyService) {
        this.requirementService = requirementService;
        this.vacancyService = vacancyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public ResponseEntity<List<Requirement>> getAllRequirements() {
        List<Requirement> vacancyList = requirementService.findAll();
        return ResponseEntity.ok().body(vacancyList);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public ResponseEntity<Requirement> getRequirementById(@PathVariable("id") Long id) {
        Requirement requirement = requirementService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Requirement with id: %d not found", id)));
        return ResponseEntity.ok().body(requirement);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public ResponseEntity<Requirement> updateRequirement(@Valid @RequestBody Requirement requirement) {
        final Requirement updatedVacancy = requirementService.update(requirement);
        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/{vacancy_id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public ResponseEntity<Requirement> createRequirement(@Valid @RequestBody Requirement requirement, @PathVariable(value = "vacancy_id") Long vacancy_id) {
        Vacancy vacancy = vacancyService.findById(vacancy_id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id " + vacancy_id));
        requirement.setVacancy(vacancy);
        requirementService.save(requirement);
        return ResponseEntity.ok(requirement);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_COWNER')")
    public void deleteRequirement(@PathVariable("id") Long id) {
        requirementService.deleteById(id);
    }

}
