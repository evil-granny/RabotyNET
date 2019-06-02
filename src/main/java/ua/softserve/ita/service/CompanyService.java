package ua.softserve.ita.service;

import ua.softserve.ita.dto.company.CompanyPaginationDto;
import ua.softserve.ita.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Optional<Company> findById(Long id);

    List<Company> findAll();

    CompanyPaginationDto findAllWithPagination(int first, int count);

    Optional<Company> save(Company company);

    Company update(Company company);

    void deleteById(Long id);

    Optional<Company> findByName(String name);

    List<Company> findByUserId(Long id);

    Optional<Company> sendMail(Company company, String hostLink);

    Optional<Company> approve(Company company, String companyToken);

    Optional<Company> findCompanyByVacancyId(Long id);

}
