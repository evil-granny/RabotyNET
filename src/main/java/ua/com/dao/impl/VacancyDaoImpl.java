package ua.com.dao.impl;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ua.com.dao.VacancyDao;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.UserPrincipal;
import ua.com.utility.QueryUtility;
import ua.com.model.Vacancy;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ua.com.utility.LoggedUserUtil.getLoggedUser;

@Repository
public class VacancyDaoImpl extends AbstractDao<Vacancy, Long> implements VacancyDao {

    private static final String ID = "id";;
    private static final String DELETE_FROM_VACANCY = "DELETE FROM Vacancy vac WHERE vac.status NOT LIKE 'OPEN'";

    @Override
    public Optional<Vacancy> findByRequirementId(Long id) {
        return QueryUtility.findOrEmpty(() -> ((Vacancy) createNamedQuery(Vacancy.FIND_BY_REQUIREMENT)
                .setParameter(ID, id)
                .getSingleResult()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAllByCompanyIdWithPagination(Long companyId, int first, int count) {
        return (List<Vacancy>) createNamedQuery(Vacancy.FIND_VACANCIES_BY_COMPANY_ID)
                .setParameter(ID, companyId)
                .setFirstResult(first)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAllByUserIdWithPagination(Long userId, int first, int count) {
        return (List<Vacancy>) createNativeQueryWithClass("SELECT vac.* FROM Vacancy vac INNER JOIN bookmark b ON " +
                "vac.vacancy_id = b.vacancy_id where b.user_id = :id AND vac.vacancy_status = 'OPEN' ORDER BY vac.vacancy_id DESC")
                .setParameter(ID, userId)
                .setFirstResult(first)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public Long getCountOfVacanciesByCompanyId(Long companyId) {
        return (Long) createNamedQuery(Vacancy.FIND_COUNT_VACANCIES_BY_COMPANY_ID)
                .setParameter(ID, companyId)
                .getSingleResult();
    }

    @Override
    public Long getCountOfVacanciesByUserId(Long userId) {
        BigInteger count = (BigInteger) createNativeQuery("SELECT COUNT(vac.vacancy_id) FROM Vacancy vac INNER JOIN bookmark b ON " +
                "vac.vacancy_id = b.vacancy_id where b.user_id = :id AND vac.vacancy_status = 'OPEN'")
                .setParameter(ID, userId)
                .getSingleResult();
        return count.longValue();
    }

    @Override
    public Long getCountOfAllVacancies() {
        return (Long) createNamedQuery(Vacancy.FIND_COUNT_ALL_VACANCIES)
                .getSingleResult();
    }

    @Override
    public Long getCountAllHotVacancies() {
        return (Long) createNamedQuery(Vacancy.FIND_COUNT_HOT_VACANCIES)
                .getSingleResult();
    }

    @Override
    public Long getCountAllClosedVacancies() {
        return (Long) createNamedQuery(Vacancy.FIND_COUNT_CLOSED_VACANCIES)
                .setParameter(ID, getLoggedUser().get().getUserId())
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAllVacanciesWithPagination(int first, int count) {
        return (List<Vacancy>) createNamedQuery(Vacancy.FIND_VACANCIES)
                .setFirstResult(first)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAllHotVacanciesWithPagination(int first, int count) {
        return (List<Vacancy>) createNamedQuery(Vacancy.FIND_ALL_HOT_VACANCIES)
                .setFirstResult(first)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAllClosedVacanciesWithPagination(int first, int count) {
        UserPrincipal loggedUser = getLoggedUser()
                .orElseThrow(() -> new ResourceNotFoundException("Logged user not found"));
        return (List<Vacancy>) createNamedQuery(Vacancy.FIND_CLOSED_VACANCIES)
                .setParameter(ID, loggedUser.getUserId())
                .setFirstResult(first)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public void deleteAllClosedVacancies() {
        Query namedQuery = createNamedQuery(DELETE_FROM_VACANCY);
        namedQuery.executeUpdate();
    }

}
