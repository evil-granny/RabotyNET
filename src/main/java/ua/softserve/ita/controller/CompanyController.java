package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CompanyController {

    @Resource(name = "companyService")
    private Service<Company> companyService;

    @GetMapping(value = "/company/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable("id") long id) {
        Company company = companyService.findById(id);
        return ResponseEntity.ok().body(company);
    }

    @GetMapping("/companies")
    public String getAll(ModelMap model) {
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        model.addAttribute("hello", "hello");
        return "companies";
    }

    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Company company) {
        companyService.update(company, id);
        return ResponseEntity.ok().body("Company has been updated successfully.");
    }

    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        companyService.deleteById(id);
        return ResponseEntity.ok().body("Company has been deleted successfully.");
    }

    @PostMapping("/insertCompany")
    public ResponseEntity<?> insert(@RequestBody Company company) {
        Company newCompany = companyService.insert(company);
        return ResponseEntity.ok().body("New Company has been saved with ID:" + newCompany.getCompanyId());
    }

}
