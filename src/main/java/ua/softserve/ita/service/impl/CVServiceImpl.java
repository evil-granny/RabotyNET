package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.CVDao;
import ua.softserve.ita.dao.PersonDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.model.Job;
import ua.softserve.ita.model.Skill;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.CVService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@Service
@Transactional
public class CVServiceImpl implements CVService {

    private final CVDao cvDao;
    private final UserDao userDao;
    private final PersonDao personDao;

    @Autowired
    public CVServiceImpl(CVDao cvDao, UserDao userDao, PersonDao personDao) {
        this.cvDao = cvDao;
        this.userDao = userDao;
        this.personDao = personDao;
    }

    @Override
    public Optional<CV> findById(Long id) {
        return cvDao.findById(id);
    }

    @Override
    public List<CV> findAll() {
        return cvDao.findAll();
    }

    @Override
    public CV save(CV cv) {

        Person person = personDao.findById(getLoggedUser().get().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Person was not found")));
        cv.setPerson(person);

        Set<Skill> skills = cv.getSkills();
        Set<Job> jobs = cv.getJobs();
        skills.forEach(x -> x.setCv(cv));
        jobs.forEach(x -> x.setCv(cv));


        return cvDao.save(cv);
    }

    @Override
    public CV update(CV cv) {

        if (getLoggedUser().isPresent()) {
            User user = userDao.findById(getLoggedUser().get().getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person was not found"));
            cv.getPerson().setUser(user);
        }

        Set<Skill> skills = cv.getSkills();
        Set<Job> jobs = cv.getJobs();
        skills.forEach(x -> x.setCv(cv));
        jobs.forEach(x -> x.setCv(cv));


        return cvDao.update(cv);
    }

    @Override
    public void deleteById(Long id) {
        cvDao.deleteById(id);
    }

    @Override
    public Optional<CV> findByUserId(Long id) {
        return cvDao.findByUserId(id);
    }

    @Override
    public Optional<CV> findByPdfName(String name) { return cvDao.findByPdfName(name); }
}
