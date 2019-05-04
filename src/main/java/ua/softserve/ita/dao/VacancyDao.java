package ua.softserve.ita.dao;

import ua.softserve.ita.model.Vacancy;

import java.util.List;

public interface VacancyDao extends BaseDao<Vacancy,Long> {
    List<Vacancy> findAllByCompanyId(Long id);

    Object createNativeQuery(String s);
}
