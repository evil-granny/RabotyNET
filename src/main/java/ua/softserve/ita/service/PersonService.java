package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Person;

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
    public Person insert(Person person) {
        System.out.println("hello2");
        return personDao.insert(person);
    }

    @Override
    public Person update(Person person, Long id) {
        return personDao.update(person, id);
    }

    @Override
    public void deleteById(Long id) {
        personDao.deleteById(id);
    }

}
