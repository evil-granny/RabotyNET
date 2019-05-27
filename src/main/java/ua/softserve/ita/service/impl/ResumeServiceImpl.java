package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.ResumeDao;
import ua.softserve.ita.dao.PersonDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Resume;
import ua.softserve.ita.model.Job;
import ua.softserve.ita.model.Skill;
import ua.softserve.ita.model.User;
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

    @Autowired
    public ResumeServiceImpl(ResumeDao resumeDao, UserDao userDao, PersonDao personDao) {
        this.resumeDao = resumeDao;
        this.userDao = userDao;
        this.personDao = personDao;
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
}
