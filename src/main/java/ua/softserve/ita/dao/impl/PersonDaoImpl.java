package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.PersonDao;
import ua.softserve.ita.model.profile.Person;

@Repository
public class PersonDaoImpl extends AbstractDao<Person, Long> implements PersonDao {

}
