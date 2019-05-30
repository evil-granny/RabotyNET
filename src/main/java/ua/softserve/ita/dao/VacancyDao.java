package ua.softserve.ita.dao;

import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyDao extends BaseDao<Vacancy, Long> {

    List<Vacancy> findAllByCompanyIdWithPagination(Long companyId, int first, int count);

    Long getCountOfVacanciesByCompanyId(Long companyId);

    Long getCountOfAllVacancies();

    Long getCountAllHotVacancies();

    Long getCountAllClosedVacancies();

    List<Vacancy> findAllVacanciesWithPagination(int first, int count);

    List<Vacancy> findAllHotVacanciesWithPagination(int first, int count);

    List<Vacancy> findAllClosedVacanciesWithPagination(int first, int count);

    Optional<Vacancy> findByRequirementId(Long id);

}
