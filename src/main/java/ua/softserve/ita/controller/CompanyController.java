package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class CompanyController {

    @Resource(name = "companyService")
    private Service<Company> companyService;

    @GetMapping(value = "/company/{id}")
    public Company getCompany(@PathVariable("id") long id) {
        return companyService.findById(id);
    }

    @GetMapping(path = {"/companies"})
    public List<Company> getAll() {
        return companyService.findAll();
    }

    @PutMapping("/updateCompany/{id}")
    public Company update(@PathVariable("id") long id, @RequestBody Company company) {
        return companyService.update(company, id);
    }

    @DeleteMapping("/deleteCompany/{id}")
    public void delete(@PathVariable("id") long id) {
        companyService.deleteById(id);
    }

    @PostMapping("/createCompany")
    public Company insert(@RequestBody Company company) {
        System.out.println(company);
        return companyService.insert(company);
    }

}
