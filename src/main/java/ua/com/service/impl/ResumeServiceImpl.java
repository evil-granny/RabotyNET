package ua.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.dao.*;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.*;
import ua.com.model.profile.Person;
import ua.com.utility.LoggedUserUtil;
import ua.com.dao.*;
import ua.com.model.*;
import ua.com.service.ResumeService;
import ua.com.service.letter.GenerateLetter;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeDao resumeDao;
    private final PersonDao personDao;
    private final VacancyDao vacancyDao;
    private final UserDao userDao;

    private final PdfResumeDao pdfResumeService;
    private final GenerateLetter generateService;

    @Autowired
    public ResumeServiceImpl(ResumeDao resumeDao, UserDao userDao, PersonDao personDao, VacancyDao vacancyDao, PdfResumeDao pdfResumeService, GenerateLetter generateService) {
        this.resumeDao = resumeDao;
        this.personDao = personDao;
        this.vacancyDao = vacancyDao;
        this.userDao = userDao;
        this.pdfResumeService = pdfResumeService;
        this.generateService = generateService;
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
        Person person = personDao.findById(LoggedUserUtil.getLoggedUser().get().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Person was not found"));
        resume.setPerson(person);

        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));

        return resumeDao.save(resume);
    }

    @Override
    public Resume update(Resume resume) {
        if (LoggedUserUtil.getLoggedUser().isPresent()) {
            User user = userDao.findById(LoggedUserUtil.getLoggedUser().get().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person was not found"));
            resume.getPerson().setUser(user);
        }
        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));

        Set<Vacancy> vacancies = resume.getVacancies();
        vacancies.clear();

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
        if (LoggedUserUtil.getLoggedUser().isPresent()) {
            User user = userDao.findById(LoggedUserUtil.getLoggedUser().get().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person was not found"));
            resume.getPerson().setUser(user);
        }
        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));

        Vacancy vacancy = vacancyDao.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", vacancyId)));

        String eMail = vacancy.getCompany().getContact().getEmail();
        PdfResume pdfResume = pdfResumeService.findByUserId(LoggedUserUtil.getLoggedUser().get().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("CV with id: %d not found"));
        generateService.sendResumePdfForVacancy(eMail, pdfResume.getPath());

        resume.getVacancies().add(vacancy);
        vacancy.getResumes().add(resume);
        vacancyDao.save(vacancy);

        return update(resume);
    }

    @Override
    public boolean existsResume(Long userId) {
        return resumeDao.findAll().stream()
                .anyMatch(x -> x.getPerson().getUserId().equals(userId));
    }

}
