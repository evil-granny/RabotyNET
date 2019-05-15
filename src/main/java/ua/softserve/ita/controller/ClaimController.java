package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.service.ClaimService;
import ua.softserve.ita.service.CompanyService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final CompanyService companyService;
    private final ClaimService claimService;

    public ClaimController(CompanyService companyService, ClaimService claimService) {
        this.companyService = companyService;
        this.claimService = claimService;
    }

    @PostMapping
    public Company createClaim(@RequestBody Claim claim) {

        Company company = claim.getCompany();

        claimService.save(claim);

        return companyService.update(company);
    }

    @GetMapping(value = {"/{companyId}"})
    public List<Claim> findClaims(@PathVariable("companyId") long companyId) {
        return claimService.findAllByCompanyId(companyId);
    }

    @DeleteMapping("/{id}")
    public void deleteClaim(@PathVariable("id") long id) {
        claimService.deleteById(id);
    }

}
