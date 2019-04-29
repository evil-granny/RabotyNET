package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.GenerateLetter;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class CompanyController {

    @Resource(name = "companyService")
    private Service<Company> companyService;

    @Resource(name = "userService")
    private Service<User> userService;

    @Autowired
    GenerateLetter letterService;

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
        if(!Company.isValid(company))
            return null;

        return companyService.update(company);
    }

    @PutMapping("/approveCompany")
    public Company approve(@RequestBody Company company, final HttpServletRequest request) {
        letterService.sendCompanyApprove(company, getAppUrl(request) + "/approveCompany/" + company.getCompanyId());
        company.setEmailSent(true);

        return companyService.update(company);
    }

    @DeleteMapping("/deleteCompany/{id}")
    public void delete(@PathVariable("id") long id) {
        companyService.deleteById(id);
    }

    @PostMapping("/createCompany")
    public Company create(@RequestBody Company company) {
        company.setUser(userService.findById(1L));

        if(!Company.isValid(company))
            return null;

        return companyService.create(company);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
