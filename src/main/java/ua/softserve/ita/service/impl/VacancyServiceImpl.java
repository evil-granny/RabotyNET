package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.CompanyDao;
import ua.softserve.ita.dao.RequirementDao;
import ua.softserve.ita.dao.VacancyDao;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.VacancyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class VacancyServiceImpl implements VacancyService {

    private final VacancyDao vacancyDao;
    private final RequirementDao requirementDao;
    private final CompanyDao companyDao;


    @Autowired
    public VacancyServiceImpl(VacancyDao vacancyDao, RequirementDao requirementDao, CompanyDao companyDao) {
        this.vacancyDao = vacancyDao;
        this.requirementDao = requirementDao;
        this.companyDao = companyDao;
    }

    @Override
    public Optional<Vacancy> findById(Long id) {
        return vacancyDao.findById(id);
    }

    @Override
    public List<Vacancy> findAll() {
        return vacancyDao.findAll();
    }

    @Override
    public List<Vacancy> findAllVacanciesByCompanyIdWithPagination(Long companyId, int first, int count) {
        return vacancyDao.findAllVacanciesByCompanyIdWithPagination(companyId, first, count);
    }

    @Override
    public List<Vacancy> findAllVacanciesWithPagination(int first, int count) {
        return vacancyDao.findWithPagination(first, count);
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancyDao.save(vacancy);
    }

    @Override
    public Vacancy update(Vacancy vacancy) {
        Company company = companyDao.findByVacancyId(vacancy.getVacancyId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Company by company id: %d not found", vacancy.getVacancyId())));
        vacancy.setCompany(company);
        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        requirements.forEach(requirementDao::update);
        return vacancyDao.update(vacancy);
    }

    @Override
    public void deleteById(Long id) {
        vacancyDao.deleteById(id);
    }

    @Override
    public Long getCountOfVacanciesByCompanyId(Long companyId) {
        return vacancyDao.getCountOfVacanciesByCompanyId(companyId);
    }

    @Override
    public Long getCountOfAllVacancies() {
        return vacancyDao.getCountOfAllVacancies();
    }
}
