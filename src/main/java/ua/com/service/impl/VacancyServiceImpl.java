package ua.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ua.com.dao.CompanyDao;
import ua.com.dao.RequirementDao;
import ua.com.dao.VacancyDao;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.Requirement;
import ua.com.model.Resume;
import ua.com.utility.LoggedUserUtil;
import ua.com.dto.VacancyDto;
import ua.com.model.Company;
import ua.com.model.Vacancy;
import ua.com.model.enumtype.VacancyStatus;
import ua.com.service.ResumeService;
import ua.com.service.vacancy.VacancyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class VacancyServiceImpl implements VacancyService {

    private static final int COUNT_VACANCIES_ON_SINGLE_PAGE = 9;
    private static final int COUNT_VACANCIES_ON_VIEW_COMPANY_PAGE = 4;

    private final VacancyDao vacancyDao;
    private final RequirementDao requirementDao;
    private final CompanyDao companyDao;

    private final ResumeService resumeService;

    @Autowired
    public VacancyServiceImpl(VacancyDao vacancyDao, RequirementDao requirementDao, CompanyDao companyDao, ResumeService resumeService) {
        this.vacancyDao = vacancyDao;
        this.requirementDao = requirementDao;
        this.companyDao = companyDao;
        this.resumeService = resumeService;
    }

    @Override
    public Optional<Vacancy> findById(Long id) {
        Optional<Vacancy> vacancy = vacancyDao.findById(id);
        vacancy.ifPresent(vac -> {
            if (!CollectionUtils.isEmpty(vac.getUsers())) {
                vac.setMarkedAsBookmark(true);
            }
        });
        return vacancy;
    }

    @Override
    public List<Vacancy> findAll() {
        return vacancyDao.findAll();
    }

    @Override
    public VacancyDto findAllVacanciesByCompanyId(Long companyId, int first) {
        return new VacancyDto(vacancyDao.getCountOfVacanciesByCompanyId(companyId),
                vacancyDao.findAllByCompanyIdWithPagination(companyId, first, COUNT_VACANCIES_ON_VIEW_COMPANY_PAGE));
    }

    @Override
    public VacancyDto findAllHotVacanciesWithPagination(int first) {
        return new VacancyDto(vacancyDao.getCountAllHotVacancies(),
                vacancyDao.findAllHotVacanciesWithPagination(first, COUNT_VACANCIES_ON_SINGLE_PAGE));
    }

    @Override
    public VacancyDto findAllClosedVacanciesWithPagination(int first) {
        return new VacancyDto(vacancyDao.getCountAllClosedVacancies(),
                vacancyDao.findAllClosedVacanciesWithPagination(first, COUNT_VACANCIES_ON_SINGLE_PAGE));
    }

    @Override
    @Transactional
    public VacancyDto findAllVacanciesWithPagination(int first, Long userId) {
        List<Vacancy> vacancies = vacancyDao.findAllVacanciesWithPagination(first, COUNT_VACANCIES_ON_SINGLE_PAGE);

        vacancies.forEach(vacancy -> {
            if (vacancy.getUsers().stream().anyMatch(user -> user.getUserId().equals(userId))) {
                vacancy.setMarkedAsBookmark(true);
            }
        });
        return new VacancyDto(vacancyDao.getCountOfAllVacancies(),
                vacancies);
    }

    @Override
    public Vacancy save(Vacancy vacancy, Long companyId) {
        Company company = companyDao.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + companyId));
        vacancy.setCompany(company);

        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(requirement -> requirement.setVacancy(vacancy));
        vacancy.setVacancyStatus(VacancyStatus.OPEN);
        vacancyDao.save(vacancy);
        requirements.forEach(requirementDao::save);

        return vacancyDao.save(vacancy);
    }

    @Override
    public Vacancy update(Vacancy vacancy) {
        Company company = companyDao.findByVacancyId(vacancy.getVacancyId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Company by vacancy id: %d not found", vacancy.getVacancyId())));

        if (company.getUser().getUserId().equals(LoggedUserUtil.getLoggedUser().get().getUserId())) {
            vacancy.setCompany(company);
            Set<Requirement> requirements = vacancy.getRequirements();
            requirements.forEach(requirement -> requirement.setVacancy(vacancy));
            requirements.stream()
                    .filter(requirement -> requirement.getRequirementId() == null)
                    .forEach(requirementDao::save);
            requirements.forEach(requirementDao::update);

            Set<Resume> resumes = vacancyDao.findById(vacancy.getVacancyId()).get().getResumes();
            vacancy.setResumes(resumes);
            resumes.forEach(resumeService::update);
        }

        return vacancyDao.update(vacancy);
    }

    @Override
    public void deleteById(Long id) {
        Company company = companyDao.findByVacancyId(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Company by vacancy id: %d not found", id)));

        if (company.getUser().getUserId().equals(LoggedUserUtil.getLoggedUser().get().getUserId())) {
            vacancyDao.deleteById(id);
        }
    }

    @Override
    @Scheduled(cron = "${cron.cleanClosedVacancies}")
    public void deleteAllClosedVacancies() {
        vacancyDao.deleteAllClosedVacancies();
    }

}
