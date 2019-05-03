package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Contact;

import javax.annotation.Resource;
import java.util.List;

@Component("contactsService")
@org.springframework.stereotype.Service
@Transactional
public class ContactsService implements Service<Contact> {

    @Resource(name = "contactsDao")
    private Dao<Contact> contactsDao;

    @Override
    public Contact findById(Long id) {
        return contactsDao.findById(id);
    }

    @Override
    public List<Contact> findAll() {
        return contactsDao.findAll();
    }

    @Override
    public Contact create(Contact contact) {
        return contactsDao.create(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return contactsDao.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        contactsDao.deleteById(id);
    }

}
