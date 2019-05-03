package ua.softserve.ita.service.profile;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@Component("personService")
@org.springframework.stereotype.Service
@Transactional
public class PersonService implements Service<Person> {

    @Resource(name = "personDao")
    private Dao<Person> personDao;

    @Override
    public Person findById(Long id) {
        return personDao.findById(id);
    }

    @Override
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public Person create(Person person) {
        return personDao.create(person);
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
