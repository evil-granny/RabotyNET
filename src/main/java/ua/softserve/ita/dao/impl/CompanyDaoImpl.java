package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.CompanyDao;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.utility.QueryUtility;

import java.util.Optional;

@Repository
public class CompanyDaoImpl extends AbstractDao<Company, Long> implements CompanyDao {
    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public Optional<Company> findByVacancyId(Long id) {
        return QueryUtility.findOrEmpty(() -> ((Company) createNamedQuery(Company.FIND_BY_VACANCY_ID)
                .setParameter(ID, id)
                .getSingleResult()));
    }

    @Override
    public Long getCompaniesCount() {
        return (Long) createNamedQuery(Company.FIND_COUNT_COMPANY)
                .getSingleResult();
    }

    @Override
    public Optional<Company> findByName(String name) {
        return QueryUtility.findOrEmpty(() -> ((Company) createNamedQuery(Company.FIND_BY_COMPANY_NAME)
                .setParameter(NAME, name)
                .getSingleResult()));
    }
}
