package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.VacancyDao;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.utility.QueryUtility;

import java.util.List;
import java.util.Optional;

@Repository
public class VacancyDaoImpl extends AbstractDao<Vacancy, Long> implements VacancyDao {
    private static final String ID = "id";

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAllByCompanyId(Long id) {
        return (List<Vacancy>)createNamedQuery(Vacancy.FIND_BY_COMPANY)
                .setParameter(ID, id)
                .getResultList();
    }

    @Override
    public Optional<Vacancy> findByRequirementId(Long id) {
        return QueryUtility.findOrEmpty(() -> ((Vacancy) createNamedQuery(Vacancy.FIND_BY_REQUIREMENT)
                .setParameter(ID, id)
                .getSingleResult()));
    }

}
