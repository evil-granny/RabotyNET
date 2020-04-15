package ua.com.dao.impl.profile;

import org.springframework.stereotype.Repository;
import ua.com.dao.PersonDao;
import ua.com.model.profile.Person;
import ua.com.dao.impl.AbstractDao;

@Repository
public class PersonDaoImpl extends AbstractDao<Person, Long> implements PersonDao {
}
