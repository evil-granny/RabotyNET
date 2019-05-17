package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.CompanyDTO.CompanyPaginationDTO;
import ua.softserve.ita.exception.CompanyAlreadyExistException;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.User;
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

    @GetMapping(value = "/{name}")
    public Company getCompanyByName(@PathVariable("name") String name) {
        return companyService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + name));
    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.findAll();
    }

    @GetMapping(path = {"/{first}/{count}"})
    public CompanyPaginationDTO getAllWithPagination(@PathVariable("first") int first, @PathVariable("count") int count) {
        return companyService.findAllWithPagination(first, count);
    }

    @GetMapping(value = "/my")
    public List<Company> getAllByUser() {
        return companyService.findByUserId(getLoggedUser().get().getUserID());
    }

    @PutMapping
    public Company update(@Valid @RequestBody Company company) {
        return companyService.update(company);
    }

    @PutMapping("/approve")
    public Company approve(@RequestBody Company company, final HttpServletRequest request) {
        letterService.sendCompanyApprove(company, getAppUrl(request) + "/approveCompany/" + company.getName());
        company.setStatus(Status.MAIL_SENT);

        return companyService.update(company);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        companyService.deleteById(id);
    }

    @PostMapping
    public Company create(@Valid @RequestBody Company company) {

        User user = new User();
        user.setUserId(getLoggedUser().get().getUserID());
        company.setUser(user);

        return companyService.save(company).orElseThrow(() -> new CompanyAlreadyExistException("Company already exists with name " + company.getName()));
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
