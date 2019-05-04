package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Status;
import ua.softserve.ita.service.ClaimService;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.StatusService;
import ua.softserve.ita.service.letter.GenerateLetter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class CompanyController {

    private final CompanyService companyService;
    private final ClaimService claimService;
    private final StatusService statusService;
    private final GenerateLetter letterService;

    public CompanyController(CompanyService companyService, ClaimService claimService, StatusService statusService, GenerateLetter letterService) {
        this.companyService = companyService;
        this.claimService = claimService;
        this.statusService = statusService;
        this.letterService = letterService;
    }

    @GetMapping(value = "/company/{id}")
    public Optional<Company> getCompany(@PathVariable("id") long id) {
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

        if(company.getStatus().isReadToDelete()) {
            statusService.deleteById(company.getStatus().getStatusId());
        }

        return companyService.update(company);
    }

    @PutMapping("/approveCompany")
    public Company approve(@RequestBody Company company, final HttpServletRequest request) {
        letterService.sendCompanyApprove(company, getAppUrl(request) + "/approveCompany/" + company.getCompanyId());
        company.getStatus().setMailSent(true);

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

        return companyService.save(company);
    }

    @PostMapping("/createClaim")
    public Company createClaim(@RequestBody Claim claim) {

        Company company = claim.getCompany();

        claimService.save(claim);

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

    @PostMapping("/createStatus")
    public Status createStatus(@RequestBody Status status) {
        return statusService.save(status);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
