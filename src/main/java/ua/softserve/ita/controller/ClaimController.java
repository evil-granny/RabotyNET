package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.service.ClaimService;
import ua.softserve.ita.service.CompanyService;

import java.util.List;

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

    @PostMapping(value = "/create")
    public Company createClaim(@RequestBody Claim claim) {

        Company company = claim.getCompany();

        claimService.save(claim);

        return companyService.update(company);
    }

    @GetMapping(value = {"/{claimId}"})
    public Claim findClaimById(@PathVariable("claimId") long claimId) {
        if (claimId < 0) {
            throw new IllegalArgumentException("ID can't be negative");
        }
        return claimService.findById(claimId).orElseThrow(() ->new ResourceNotFoundException("Claim not found with id " + claimId));
    }

    @GetMapping(value = {"/byCompany/{companyId}"})
    public List<Claim> findClaims(@PathVariable("companyId") long companyId) {
        return claimService.findAllByCompanyId(companyId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClaim(@PathVariable("id") long id) {
        claimService.deleteById(id);
    }

}
