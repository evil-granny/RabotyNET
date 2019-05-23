package ua.softserve.ita.service;

import ua.softserve.ita.dto.VacancyDTO.VacancyDTO;
import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {

    Optional<Vacancy> findById(Long id);

    List<Vacancy> findAll();

    VacancyDTO findAllVacanciesByCompanyId(Long companyId, int first);

    VacancyDTO findAllHotVacanciesWithPagination(int first);

    VacancyDTO findAllVacanciesWithPagination(int first);

    Vacancy save(Vacancy vacancy);

    Vacancy update(Vacancy vacancy);

    void deleteById(Long id);

}
