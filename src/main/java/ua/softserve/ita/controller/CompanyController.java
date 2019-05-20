package ua.softserve.ita.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.CompanyDTO.CompanyPaginationDTO;
import ua.softserve.ita.exception.CompanyAlreadyExistException;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.enumtype.Status;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.letter.GenerateLetter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@CrossOrigin
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final GenerateLetter letterService;

    public CompanyController(CompanyService companyService, GenerateLetter letterService) {
        this.companyService = companyService;
        this.letterService = letterService;
    }

    @GetMapping(value = "/byName/{name}")
    public Company getCompanyByName(@PathVariable("name") String name) {
        return companyService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + name));
    }

    @GetMapping(value = "/all")
    public List<Company> getAll() {
        return companyService.findAll();
    }

    @GetMapping(path = {"/all/{first}/{count}"})
    public CompanyPaginationDTO getAllWithPagination(@PathVariable("first") int first, @PathVariable("count") int count) {
        return companyService.findAllWithPagination(first, count);
    }

    @GetMapping(value = "/my")
    public List<Company> getAllByUser() {
        return companyService.findByUserId(getLoggedUser().get().getUserId());
    }

    @PutMapping(value = "/update")
    public Company update(@Valid @RequestBody Company company) {
        return companyService.update(company);
    }

    @PutMapping("/sendMail")
    public Company sendMail(@RequestBody Company company, final HttpServletRequest request) {
        letterService.sendCompanyApprove(company, getAppUrl(request) + "/approveCompany/" + company.getName());
        company.setStatus(Status.MAIL_SENT);

        return companyService.update(company);
    }

    @PutMapping("/approve")
    public Company approve(@RequestBody Company company) {
        return companyService.approve(company).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + company.getName()));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") long id) {
        companyService.deleteById(id);
    }

    @PostMapping(value = "/create")
    public Company create(@Valid @RequestBody Company company) {
        return companyService.save(company).orElseThrow(() -> new CompanyAlreadyExistException("Company already exists with name " + company.getName()));
    }

    @GetMapping(value = "/exists/{companyName}")
    public boolean exists(@PathVariable("companyName") String companyName) {
        return companyService.findByName(companyName).isPresent();
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
