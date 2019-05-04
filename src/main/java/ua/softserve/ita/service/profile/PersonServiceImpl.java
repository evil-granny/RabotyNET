package ua.softserve.ita.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.PersonDao;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.PersonService;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personDao.findById(id);
    }

    @Override
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public Person save(Person person) {
        return personDao.save(person);
    }

    @Override
    public Person update(Person person) {
        return personDao.update(person);
    }

    @Override
    public void deleteById(Long id) {
       personDao.deleteById(id);
    }
}
