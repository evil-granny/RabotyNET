package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.service.ClaimService;
import ua.softserve.ita.service.CompanyService;

import java.util.List;

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

    @GetMapping(value = {"/byCompany/{companyId}"})
    public List<Claim> findClaims(@PathVariable("companyId") long companyId) {
        return claimService.findAllByCompanyId(companyId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClaim(@PathVariable("id") long id) {
        claimService.deleteById(id);
    }

}
