package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    @Resource(name = "vacancyService")
    private Service<Vacancy> vacancyService;

    @Resource(name = "requirementService")
    private Service<Requirement> requirementService;

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancyList = vacancyService.findAll();
        return ResponseEntity.ok().body(vacancyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyService.findById(id);
        if (vacancy == null) {
            throw new ResourceNotFoundException("Vacancy not found by id: " + id);
        }
        return ResponseEntity.ok().body(vacancy);

    }

    @PutMapping
    public ResponseEntity<Vacancy> updateVacancy(@Valid @RequestBody Vacancy vacancy) {
        final Vacancy updatedVacancy = vacancyService.update(vacancy);
        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/{company_id}")
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy, @PathVariable(value = "company_id") Long companyId) {
        Company company = new Company();
        company.setCompanyId(companyId);
        vacancy.setCompany(company);

        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        vacancyService.create(vacancy);
        requirements.forEach(e -> requirementService.create(e));

        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
