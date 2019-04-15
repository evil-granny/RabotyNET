package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Contacts;

import javax.annotation.Resource;
import java.util.List;

@Component("contactsService")
@org.springframework.stereotype.Service
@Transactional
public class ContactsService implements Service<Contacts> {

    @Resource(name = "contactsDao")
    private Dao<Contacts> contactsDao;

    @Override
    public Contacts findById(Long id) {
        return contactsDao.findById(id);
    }

    @Override
    public List<Contacts> findAll() {
        return contactsDao.findAll();
    }

    @Override
    public Long insert(Contacts contacts) {
        return contactsDao.insert(contacts);
    }

    @Override
    public Long update(Contacts contacts, Long id) {
        return contactsDao.update(contacts, id);
    }

    @Override
    public void deleteById(Long id) {
        contactsDao.deleteById(id);
    }

}
