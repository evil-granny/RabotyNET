package ua.softserve.ita.service.profile;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@Component("contactService")
@org.springframework.stereotype.Service
@Transactional
public class ContactService implements Service<Contact> {

    @Resource(name = "contactDao")
    private Dao<Contact> contactDao;

    @Override
    public Contact findById(Long id) {
        return contactDao.findById(id);
    }

    @Override
    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    @Override
    public Contact create(Contact contact) {
        return contactDao.create(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return contactDao.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        contactDao.deleteById(id);
    }

}
