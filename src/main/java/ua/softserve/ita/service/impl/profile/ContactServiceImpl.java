package ua.softserve.ita.service.impl.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.ContactDao;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.service.ContactService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactDao contactDao;

    @Autowired
    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return contactDao.findById(id);
    }

    @Override
    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    @Override
    public Contact save(Contact contact) {
        return contactDao.save(contact);
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
