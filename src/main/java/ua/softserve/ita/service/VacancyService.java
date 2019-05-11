package ua.softserve.ita.service;

import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {

    Optional<Vacancy> findById(Long id);

    List<Vacancy> findAll();

    Vacancy save(Vacancy vacancy);

    Vacancy update(Vacancy vacancy);

    void deleteById(Long id);

    List<Vacancy> findAllVacanciesByCompanyIdWithPagination(Long companyId, int first, int count);

    List<Vacancy> findAllVacanciesWithPagination(int first, int count);

    Long getCountOfVacanciesByCompanyId(Long companyId);

    Long getCountOfAllVacancies();

}
