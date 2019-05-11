package ua.softserve.ita.dao;

import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyDao extends BaseDao<Vacancy,Long> {
    List<Vacancy> findAllVacanciesByCompanyIdWithPagination(Long id, int first, int count);

    Long getCountOfVacanciesByCompanyId(Long companyId);

    Long getCountOfAllVacancies();

    Optional<Vacancy> findByRequirementId(Long id);
}
