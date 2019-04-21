package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.IService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
public class VacancyController {

    @Resource(name = "vacancyService")
    private IService<Vacancy> vacancyService;

    @Resource(name = "requirementService")
    private IService<Requirement> requirementService;

    @GetMapping("/vacancies")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancyList = vacancyService.findAll();
        return ResponseEntity.ok().body(vacancyList);
    }

    @GetMapping("/vacancy/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyService.findById(id);
        return ResponseEntity.ok().body(vacancy);
    }

    @PutMapping("/vacancy/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable("id") Long id, @Valid @RequestBody Vacancy vacancy) {
        Company company = new Company();
        company.setCompanyId(1L);
        vacancy.setCompany(company);

       /* Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));*/
        final Vacancy updatedVacancy = vacancyService.update(vacancy, id);
        //requirements.forEach(e -> requirementService.update(e,id));

        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/vacancy/{company_id}")
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy, @PathVariable(value = "company_id") Long companyId) {
        Company company = new Company();
        company.setCompanyId(companyId);
        vacancy.setCompany(company);

        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        vacancyService.insert(vacancy);
        requirements.forEach(e -> requirementService.insert(e));
        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/vacancy/{id}")
    public Map<String, Boolean> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
