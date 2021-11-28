package ua.com.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.model.Vacancy;

import java.util.List;
import java.util.Optional;


public interface VacancyDao extends BaseDao<Vacancy, Long> {

    List<Vacancy> findAllByCompanyIdWithPagination(Long companyId, int first, int count);

    List<Vacancy> findAllByUserIdWithPagination(Long companyId, int first, int count);

    Long getCountOfVacanciesByCompanyId(Long companyId);

    Long getCountOfVacanciesByUserId(Long companyId);

    Long getCountOfAllVacancies();

    Long getCountAllHotVacancies();

    Long getCountAllClosedVacancies();

    List<Vacancy> findAllVacanciesWithPagination(int first, int count);

    List<Vacancy> findAllHotVacanciesWithPagination(int first, int count);

    List<Vacancy> findAllClosedVacanciesWithPagination(int first, int count);

    Optional<Vacancy> findByRequirementId(Long id);

    void deleteAllClosedVacancies();

}
