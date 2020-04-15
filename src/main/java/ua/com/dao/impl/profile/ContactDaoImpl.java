package ua.com.dao.impl.profile;

import org.springframework.stereotype.Repository;
import ua.com.model.profile.Contact;
import ua.com.dao.ContactDao;
import ua.com.dao.impl.AbstractDao;

@Repository
public class ContactDaoImpl extends AbstractDao<Contact, Long> implements ContactDao {
}
