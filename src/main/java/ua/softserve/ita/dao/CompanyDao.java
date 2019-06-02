package ua.softserve.ita.dao;

import ua.softserve.ita.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyDao extends BaseDao<Company, Long> {

    Optional<Company> findByVacancyId(Long id);

    Long getCompaniesCount();

    Optional<Company> findByName(String name);

    List<Company> findByUserId(Long id);

}
