package ua.softserve.ita.service;

import ua.softserve.ita.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Optional<Company> findById(Long id);

    List<Company> findAll();

    List<Company> findAllWithPagination(int first, int count);

    Company save(Company company);

    Company update(Company company);

    void deleteById(Long id);
}
