package ua.softserve.ita.service;

import ua.softserve.ita.dto.VacancyDto;
import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {

    Optional<Vacancy> findById(Long id);

    List<Vacancy> findAll();

    VacancyDto findAllVacanciesByCompanyId(Long companyId, int first);

    VacancyDto findAllHotVacanciesWithPagination(int first);

    VacancyDto findAllClosedVacanciesWithPagination(int first);

    VacancyDto findAllVacanciesWithPagination(int first);

    Vacancy save(Vacancy vacancy, Long companyId);

    Vacancy update(Vacancy vacancy);

    void deleteById(Long id);

}
