package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.RequirementService;
import ua.softserve.ita.service.VacancyService;

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
    private final VacancyService vacancyService;
    private final RequirementService requirementService;

    @Autowired
    public VacancyController(VacancyService vacancyService, RequirementService requirementService) {

        this.vacancyService = vacancyService;
        this.requirementService = requirementService;
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancyList = vacancyService.findAll();
        return ResponseEntity.ok().body(vacancyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable("id") Long id) {
        Vacancy vacancy = vacancyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", id)));
        return ResponseEntity.ok().body(vacancy);
    }

    @PutMapping
    public ResponseEntity<Vacancy> updateVacancy(@Valid @RequestBody Vacancy vacancy) {
        final Vacancy updatedVacancy = vacancyService.update(vacancy);
        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/createVacancy/{company_id}")
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy, @PathVariable(value = "company_id") Long companyId) {
        Company company = new Company();
        company.setCompanyId(companyId);
        vacancy.setCompany(company);

        System.out.println(vacancy);
        System.out.println(companyId);

        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        vacancyService.save(vacancy);
        requirements.forEach(requirementService::save);

        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Vacancy was deleted", Boolean.TRUE);
        return response;
    }
    @GetMapping("/byCompanyId/{companyId}/{first}/{count}")
    public ResponseEntity<List<Vacancy>> findAllVacanciesByCompanyIdWithPagination(@PathVariable("companyId") Long companyId,
                                                                                   @PathVariable("first") int first,
                                                                                   @PathVariable("count") int count){
        List<Vacancy> allByCompanyId = vacancyService.findAllByCompanyId(companyId, first, count);
        return ResponseEntity.ok().body(allByCompanyId);
    }
    @GetMapping("count/{companyId}")
    public ResponseEntity<Long> getCountOfVacancies(@PathVariable("companyId") Long companyId){
        return ResponseEntity.ok().body(vacancyService.getCountOfVacancies(companyId));
    }
}
