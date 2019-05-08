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

    @Override
    public Optional<Company> findByVacancyId(Long id) {
        return QueryUtility.findOrEmpty(() -> ((Company) createNamedQuery(Company.FIND_BY_VACANCY_ID)
                .setParameter(ID, id)
                .getSingleResult()));
    }
}
