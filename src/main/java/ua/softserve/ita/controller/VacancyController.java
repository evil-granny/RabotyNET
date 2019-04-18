package ua.softserve.ita.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.model.enumtype.TypeOfEmployment;
import ua.softserve.ita.service.IService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VacancyController {

    @Resource(name = "vacancyService")
    private IService<Vacancy> vacancyService;

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
        final Vacancy updatedVacancy = vacancyService.update(vacancy, id);
        return ResponseEntity.ok(updatedVacancy);
    }

    @PostMapping("/vacancy")
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy) throws IOException {
     /*   Vacancy vacancy1 = new Vacancy();
        vacancy1.setVacancyId(1L);
        vacancy1.setTypeOfEmployment(TypeOfEmployment.HOURLY);
        vacancy1.setPosition("junior");
        vacancy1.setSalary(2000);

        Company company = new Company();
        company.setWebsite("http://");
        company.setCompanyId(1L);
        company.setName("SoftServe");

        List<Requirement> requirement = new ArrayList<>();
        requirement.add(new Requirement("Java",vacancy1));
        vacancy1.setCompany(company);
        vacancy1.setRequirements(requirement);

        writeJsonFromVacancy(vacancy1);*/
        vacancyService.insert(vacancy);
        return ResponseEntity.ok(vacancy);
    }

    private void writeJsonFromVacancy(Vacancy vacancy) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(vacancy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonInString);
      /*  GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(vacancy);
        System.out.println(jsonString);*/

       /* try {
            Files.write(Paths.get("src/main/resources/jsonFormat.json"),
                    jsonString.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
           e.printStackTrace();
        }*/
    }

    @DeleteMapping("/vacancy/{id}")
    public Map<String, Boolean> deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
