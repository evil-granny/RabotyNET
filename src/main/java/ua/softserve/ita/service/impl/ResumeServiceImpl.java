package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.ResumeDao;
import ua.softserve.ita.dao.PersonDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dao.VacancyDao;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.*;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.ResumeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeDao resumeDao;
    private final UserDao userDao;
    private final PersonDao personDao;
    private final VacancyDao vacancyDao;

    @Autowired
    public ResumeServiceImpl(ResumeDao resumeDao, UserDao userDao, PersonDao personDao, VacancyDao vacancyDao) {
        this.resumeDao = resumeDao;
        this.userDao = userDao;
        this.personDao = personDao;
        this.vacancyDao = vacancyDao;
    }

    @Override
    public Optional<Resume> findById(Long id) {
        return resumeDao.findById(id);
    }

    @Override
    public List<Resume> findAll() {
        return resumeDao.findAll();
    }

    @Override
    public Resume save(Resume resume) {

        Person person = personDao.findById(getLoggedUser().get().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Person was not found")));
        resume.setPerson(person);

        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));


        return resumeDao.save(resume);
    }

    @Override
    public Resume update(Resume resume) {
        if (getLoggedUser().isPresent()) {
            User user = userDao.findById(getLoggedUser().get().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person was not found"));
            resume.getPerson().setUser(user);
        }
        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));

        Set<Vacancy> vacancies = resume.getVacancies();
        vacancies.clear();
        vacancies.forEach(v -> v.getResumes().add(resume));
        vacancies.forEach(vacancyDao::update);

        return resumeDao.update(resume);
    }

    @Override
    public void deleteById(Long id) {
        resumeDao.deleteById(id);
    }

    @Override
    public Optional<Resume> findByUserId(Long id) {
        return resumeDao.findByUserId(id);
    }

    @Override
    public List<Resume> findResumeByVacancyId(Long vacancyId) {
        return resumeDao.findResumeByVacancyId(vacancyId);
    }

    @Override
    public Resume sendResumeOnThisVacancy(Resume resume, Long vacancyId) {
        if (getLoggedUser().isPresent()) {
            User user = userDao.findById(getLoggedUser().get().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person was not found"));
            resume.getPerson().setUser(user);
        }
        Set<Vacancy> vacancies = resume.getVacancies();
        System.out.println("Start" + vacancies.isEmpty());
        vacancies.forEach(System.out::println);
        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));
        resume.getVacancies().add(vacancyDao.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", vacancyId))));
        System.out.println(resume);
        vacancies.forEach(v -> v.getResumes().add(resume));
        vacancies.forEach(vacancyDao::update);
        return update(resume);
    }
}
