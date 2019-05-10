package ua.softserve.ita.dao;

import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyDao extends BaseDao<Vacancy,Long> {
    List<Vacancy> findAllByCompanyIdWithPagination(Long id, int first, int count);

    Long getCountOfVacancies(Long id);

    Optional<Vacancy> findByRequirementId(Long id);
}
