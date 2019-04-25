package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class RequirementController {

    @Resource(name = "requirementService")
    //@Autowired
    private Service<Requirement> requirementService;

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

    @PutMapping("/updateRequirement")
    public ResponseEntity<Requirement> updateRequirement(@Valid @RequestBody Requirement requirement) {
        final Requirement updatedVacancy = requirementService.update(requirement);
        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/createRequirement/{vacancy_id}")
    public ResponseEntity<Requirement> createRequirement(@Valid @RequestBody Requirement requirement, @PathVariable(value = "vacancy_id") Long vacancy_id) {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId(vacancy_id);
        requirement.setVacancy(vacancy);
        requirementService.create(requirement);
        return ResponseEntity.ok(requirement);
    }

    @DeleteMapping("/deleteRequirement/{id}")
    public Map<String, Boolean> deleteRequirement(@PathVariable("id") Long id) {
        requirementService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
