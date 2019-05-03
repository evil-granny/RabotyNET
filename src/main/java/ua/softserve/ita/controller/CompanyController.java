package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.service.GenerateLetter;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class CompanyController {

    @Resource(name = "companyService")
    private Service<Company> companyService;

    @Resource(name = "claimService")
    private Service<Claim> claimService;

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
        if(!Company.isValid(company))
            return null;

        return companyService.create(company);
    }

    @PostMapping("/createClaim")
    public Company createClaim(@RequestBody Claim claim) {

        Company company = claim.getCompany();

        System.out.println(claim);
        System.out.println(company);

        claimService.create(claim);

        return companyService.update(company);
    }

    @GetMapping(value = {"/findClaims/{companyId}"})
    public List<Claim> findClaims(@PathVariable("companyId") long companyId) {
        List<Claim> res = claimService.findAll().stream().filter((c) -> c.getCompany().getCompanyId().equals(companyId)).collect(Collectors.toList());
        return res;
    }

    @DeleteMapping("/deleteClaim/{id}")
    public void deleteClaim(@PathVariable("id") long id) {
        claimService.deleteById(id);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
