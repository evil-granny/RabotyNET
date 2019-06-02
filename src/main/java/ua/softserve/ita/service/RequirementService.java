package ua.softserve.ita.service;

import ua.softserve.ita.model.Requirement;

import java.util.List;
import java.util.Optional;

public interface RequirementService {

    Optional<Requirement> findById(Long id);

    List<Requirement> findAll();

    Requirement save(Requirement requirement);

    Requirement update(Requirement requirement);

    void deleteById(Long id);

}
