package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.IService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RequirementController {

    @Resource(name = "requirementService")
    private IService<Requirement> requirementService;

    @GetMapping("/requirements")
    public ResponseEntity<List<Requirement>> getAllVacancies() {
        List<Requirement> vacancyList = requirementService.findAll();
        return ResponseEntity.ok().body(vacancyList);
    }

    @GetMapping("/requirement/{id}")
    public ResponseEntity<Requirement> getVacancyById(@PathVariable("id") Long id) {
        Requirement requirement = requirementService.findById(id);
        return ResponseEntity.ok().body(requirement);
    }

    @PutMapping("/requirement/{id}")
    public ResponseEntity<Requirement> updateVacancy(@PathVariable("id") Long id, @Valid @RequestBody Requirement requirement) {
        final Requirement updatedVacancy = requirementService.update(requirement, id);
        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/requirement/{vacancy_id}")
    public ResponseEntity<Requirement> createVacancy(@Valid @RequestBody Requirement requirement, @PathVariable(value = "vacancy_id") Long vacancy_id) {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId(vacancy_id);
        requirement.setVacancy(vacancy);

        requirementService.insert(requirement);
        return ResponseEntity.ok(requirement);
    }

    @DeleteMapping("/requirement/{id}")
    public Map<String, Boolean> deleteVacancy(@PathVariable("id") Long id) {
        requirementService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
