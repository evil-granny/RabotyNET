package ua.softserve.ita.service;

import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {

    Optional<Vacancy> findById(Long id);

    List<Vacancy> findAll();

    List<Vacancy> findAllByCompanyName(String companyName, int first, int count);

    List<Vacancy> findAllVacanciesWithPagination(int first, int count);

    Vacancy save(Vacancy vacancy);

    Vacancy update(Vacancy vacancy);

    void deleteById(Long id);

    Long getCountOfVacancies(String name);

    Long getCountOfAllVacancies();

}
