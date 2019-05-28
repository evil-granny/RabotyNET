package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.*;
import ua.softserve.ita.dto.VacancyDTO.VacancyDTO;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.*;
import ua.softserve.ita.service.ResumeService;
import ua.softserve.ita.service.VacancyService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@Service
@Transactional
public class VacancyServiceImpl implements VacancyService {
    private static final int COUNT_VACANCIES_ON_SINGLE_PAGE = 9;
    private static final int COUNT_VACANCIES_ON_VIEW_COMPANY_PAGE = 4;

    private final VacancyDao vacancyDao;
    private final RequirementDao requirementDao;
    private final CompanyDao companyDao;
    private final ResumeDao resumeDao;
    private final UserDao userDao;
    private final ResumeService resumeService;

    @Autowired
    public VacancyServiceImpl(VacancyDao vacancyDao, RequirementDao requirementDao, CompanyDao companyDao, ResumeDao resumeDao, UserDao userDao, ResumeService resumeService) {
        this.vacancyDao = vacancyDao;
        this.requirementDao = requirementDao;
        this.companyDao = companyDao;
        this.resumeDao = resumeDao;
        this.userDao = userDao;
        this.resumeService = resumeService;
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
    public VacancyDTO findAllVacanciesByCompanyId(Long companyId, int first) {
        return new VacancyDTO(vacancyDao.getCountOfVacanciesByCompanyId(companyId),
                vacancyDao.findAllByCompanyIdWithPagination(companyId, first, COUNT_VACANCIES_ON_VIEW_COMPANY_PAGE));
    }

    @Override
    public VacancyDTO findAllHotVacanciesWithPagination(int first) {
        return new VacancyDTO(vacancyDao.getCountAllHotVacancies(),
                vacancyDao.findAllHotVacanciesWithPagination(first, COUNT_VACANCIES_ON_SINGLE_PAGE));
    }

    @Override
    public VacancyDTO findAllVacanciesWithPagination(int first) {
        return new VacancyDTO(vacancyDao.getCountOfAllVacancies(),
                vacancyDao.findAllVacanciesWithPagination(first, COUNT_VACANCIES_ON_SINGLE_PAGE));
    }

    @Override
    public Vacancy save(Vacancy vacancy, Long companyId) {
        Company company = companyDao.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + companyId));
        vacancy.setCompany(company);

        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        vacancyDao.save(vacancy);
        requirements.forEach(requirementDao::save);
        return vacancyDao.save(vacancy);
    }

    @Override
    public Optional<Vacancy> sendResume(Vacancy vacancy, Set<Resume> resumes) {
        Optional<Vacancy> result = vacancyDao.findById(vacancy.getVacancyId());
        if(result.isPresent()) {
            vacancy = result.get();
            vacancy.setResumes(resumes);
            resumes.forEach(resumeDao::update);
            vacancyDao.update(vacancy);
        }
        return result;
    }

    @Override
    public Vacancy update(Vacancy vacancy) {
        Company company = companyDao.findByVacancyId(vacancy.getVacancyId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Company by vacancy id: %d not found", vacancy.getVacancyId())));
        if (company.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
            vacancy.setCompany(company);
            Set<Requirement> requirements = vacancy.getRequirements();
            requirements.forEach(e -> e.setVacancy(vacancy));
            requirements.stream().filter(requirement -> requirement.getRequirementId() == null).forEach(requirementDao::save);
            requirements.forEach(requirementDao::update);
            Set<Resume> resumes = vacancyDao.findById(vacancy.getVacancyId()).get().getResumes();
            resumes.forEach(r -> r.getVacancies().add(vacancy));
            resumes.forEach(resumeService::update);
        }
        return vacancy;
    }

    @Override
    public void deleteById(Long id) {
        Company company = companyDao.findByVacancyId(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Company by vacancy id: %d not found", id)));
        if (company.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
            vacancyDao.deleteById(id);
        }
    }

    @Override
    public Resume sendResumeOnThisVacancy(Resume resume) {
//        vacancyDao.insertIntoVacancyResume();

        if (getLoggedUser().isPresent()) {
            User user = userDao.findById(getLoggedUser().get().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person was not found"));
            resume.getPerson().setUser(user);
        }
        Set<Vacancy> vacancies = resume.getVacancies();
        System.out.println("Start"+vacancies.isEmpty());
        vacancies.forEach(System.out::println);

//        vacancies.clear();

        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));
        resume.getVacancies().add(vacancyDao.findById(8L).orElseThrow(()->new ResourceNotFoundException("fggffg")));




//        vacancies.forEach(v -> v.setResumes(resume));

        System.out.println(resume);
        vacancies.forEach(v -> v.getResumes().add(resume));
//        vacancies.forEach(System.out::println);
//        System.out.println("End "+vacancies.isEmpty());
//
        vacancies.forEach(vacancyDao::update);


//        resumeDao.save(resume);

       return resumeService.update(resume);
    }
}
