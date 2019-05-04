package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.VacancyDao;
import ua.softserve.ita.model.Vacancy;

import java.util.List;

@Repository
public class VacancyDaoImpl extends AbstractDao<Vacancy,Long> implements VacancyDao {
    private static final String ID = "id";

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAllByCompanyId(Long id) {
        return null;
        /*(List<Vacancy>)createNamedQuery(Vacancy.FIND_BY_COMPANY)
                .setParameter(ID, id)
                .getResultList();*/
    }

}
