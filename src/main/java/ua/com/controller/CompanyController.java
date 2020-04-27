package ua.com.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.exception.CompanyAlreadyExistException;
import ua.com.exception.ResourceNotFoundException;
import ua.com.utility.LoggedUserUtil;
import ua.com.dto.company.CompanyPaginationDto;
import ua.com.model.Company;
import ua.com.service.CompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/byName/{name}")
    public Company getCompanyById(@PathVariable("name") String name) {
        return companyService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + name));
    }

    @GetMapping(value = "/all")
    public List<Company> getAll() {
        return companyService.findAll();
    }

    @GetMapping(path = {"/all/{first}/{count}"})
    public CompanyPaginationDto getAllWithPagination(@PathVariable("first") int first, @PathVariable("count") int count) {
        return companyService.findAllWithPagination(first, count);
    }

    @GetMapping(value = "/my")
    public List<Company> getAllByUser() {
        return companyService.findByUserId(LoggedUserUtil.getLoggedUser().get().getUserId());
    }

    @PutMapping(value = "/update")
    public Company update(@Valid @RequestBody Company company) {
        return companyService.update(company);
    }

    @PutMapping("/sendMail")
    public Company sendMail(@RequestBody Company company, final HttpServletRequest request) {
        return companyService.sendMail(company, getAppUrl(request)).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + company.getName()));
    }

    @PutMapping("/approve/{token}")
    public Company approve(@PathVariable("token") String token, @RequestBody Company company) {
        return companyService.approve(company, token).orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + company.getName()));
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

    @GetMapping(value = "/byVacancyId/{id}")
    public Company getCompanyByVacancyId(@PathVariable("id") Long id) {
        return companyService.findCompanyByVacancyId(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with vacancy id " + id));
    }

    @GetMapping(value = "/byId/{companyId}")
    public Company getCompanyById(@PathVariable("companyId") Long companyId) {
        return companyService.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + companyId));
    }

}
