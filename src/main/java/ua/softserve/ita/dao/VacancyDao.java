package ua.softserve.ita.dao;

import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyDao extends BaseDao<Vacancy,Long> {
    List<Vacancy> findAllByCompanyNameWithPagination(String name, int first, int count);

    Long getCountOfVacanciesByCompanyName(String companyName);

    Long getCountOfAllVacancies();

    Long getCountAllHotVacancies();

    List<Vacancy> findAllVacanciesWithPagination(int first,int count);

    List<Vacancy> findAllHotVacanciesWithPagination(int first,int count);

    Optional<Vacancy> findByRequirementId(Long id);
}
