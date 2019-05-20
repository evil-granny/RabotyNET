package ua.softserve.ita.service;

import ua.softserve.ita.dto.VacancyDTO.VacancyDTO;
import ua.softserve.ita.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyService {

    Optional<Vacancy> findById(Long id);

    List<Vacancy> findAll();

    VacancyDTO findAllByCompanyName(String companyName, int first, int count);

    VacancyDTO findAllHotVacanciesWithPagination(int first, int count);

    VacancyDTO findAllVacanciesWithPagination(int first, int count);

    Vacancy save(Vacancy vacancy);

    Vacancy update(Vacancy vacancy);

    void deleteById(Long id);

}
