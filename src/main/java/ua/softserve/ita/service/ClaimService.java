package ua.softserve.ita.service;

import ua.softserve.ita.model.Claim;

import java.util.List;
import java.util.Optional;

public interface ClaimService {

    Optional<Claim> findById(Long id);

    List<Claim> findAll();

    Claim save(Claim claim);

    Claim update(Claim claim);

    void deleteById(Long id);

    List<Claim> findAllByCompanyId(Long id);

}
