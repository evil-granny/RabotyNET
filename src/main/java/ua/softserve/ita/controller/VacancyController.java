package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.RequirementService;
import ua.softserve.ita.service.VacancyService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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

    @PostMapping("/createVacancy/{companyName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy,  @PathVariable(value = "companyName") String companyName) {
        Company company = companyService.findByName(companyName).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + companyName));
        vacancy.setCompany(company);
        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        vacancyService.save(vacancy);
        requirements.forEach(requirementService::save);

        return ResponseEntity.ok(vacancy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Map<String, Boolean> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Vacancy was deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/{first}/{count}")
    public ResponseEntity<List<Vacancy>> findAllVacanciesWithPagination(@PathVariable("first") int first, @PathVariable("count") int count) {
        List<Vacancy> allVacanciesByCompanyId = vacancyService.findAllVacanciesWithPagination(first, count);
        List<Vacancy> collect = allVacanciesByCompanyId.stream().sorted(Comparator.comparing(Vacancy::getVacancyId)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    @GetMapping("/{companyName}/{first}/{count}")
    public ResponseEntity<List<Vacancy>> findAllVacanciesByCompanyNameWithPagination(@PathVariable("companyName") String companyName,
                                                                                   @PathVariable("first") int first,
                                                                                   @PathVariable("count") int count) {
        List<Vacancy> allByCompanyId = vacancyService.findAllByCompanyName(companyName, first, count);
        return ResponseEntity.ok().body(allByCompanyId);
    }

    @GetMapping("getCount/{companyName}")
    public ResponseEntity<Long> getCountOfVacanciesByCompanyId(@PathVariable("companyName") String companyName) {
        return ResponseEntity.ok().body(vacancyService.getCountOfVacancies(companyName));
    }

    @GetMapping("/getCountAll")
    public ResponseEntity<Long> getCountOfAllVacancies() {
        return ResponseEntity.ok().body(vacancyService.getCountOfAllVacancies());
    }
}
