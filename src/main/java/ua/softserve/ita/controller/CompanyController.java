package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class CompanyController {

    @Resource(name = "companyService")
    private Service<Company> companyService;

    @Resource(name = "userService")
    private Service<User> userService;

    @GetMapping(value = "/company/{id}")
    public Company getCompany(@PathVariable("id") long id) {
        return companyService.findById(id);
    }

    @GetMapping(path = {"/companies"})
    public List<Company> getAll() {
        return companyService.findAll();
    }

    @PutMapping("/updateCompany")
    public Company update(@RequestBody Company company) {
        return companyService.update(company);
    }

    @DeleteMapping("/deleteCompany/{id}")
    public void delete(@PathVariable("id") long id) {
        companyService.deleteById(id);
    }

    @PostMapping("/createCompany")
    public Company create(@RequestBody Company company) {
        company.setUser(userService.findById(1L));
        System.out.println(company);
        return companyService.create(company);
    }

}
