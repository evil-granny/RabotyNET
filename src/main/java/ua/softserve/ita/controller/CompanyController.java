package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.CompanyAlreadyExistException;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Status;
import ua.softserve.ita.service.ClaimService;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.StatusService;
import ua.softserve.ita.service.letter.GenerateLetter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/companies")
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

//    @GetMapping(value = "/{id}")
//    public Company getCompany(@PathVariable("id") long id) {
//
//        System.out.println(companyService.findById(id));
//        return companyService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
//    }

    @GetMapping(value = "/{name}")
    public Company getCompanyByName(@PathVariable("name") String name) {
        return companyService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + name));
    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.findAll();
    }

    @GetMapping(path = {"/{first}/{count}"})
    public List<Company> getAllWithPagination(@PathVariable("first") int first, @PathVariable("count") int count) {
        return companyService.findAllWithPagination(first, count);
    }

    @GetMapping(path = {"/count"})
    public Long getCountOfVacancies(){
        return companyService.getCompaniesCount();
    }

    @PutMapping
    public Company update(@Valid @RequestBody Company company) {
        if(company.getStatus() != null && company.getStatus().isReadyToDelete()) {
            long status_id = company.getStatus().getStatusId();
            company.setStatus(null);
            companyService.update(company);
            statusService.deleteById(status_id);
            return company;
        }

        return companyService.update(company);
    }

    @PutMapping("/approve")
    public Company approve(@RequestBody Company company, final HttpServletRequest request) {
        letterService.sendCompanyApprove(company, getAppUrl(request) + "/approveCompany/" + company.getName());
        company.getStatus().setMailSent(true);

        return companyService.update(company);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        companyService.deleteById(id);
    }

    @PostMapping
    public Company create(@Valid @RequestBody Company company) {

        return companyService.save(company).orElseThrow(() -> new CompanyAlreadyExistException("Company already exists with name " + company.getName()));
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
