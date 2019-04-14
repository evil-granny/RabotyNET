package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Address;
import ua.softserve.ita.model.Person;

import javax.annotation.Resource;
import java.util.List;

@Component("addressService")
@org.springframework.stereotype.Service
@Transactional
public class AddressService implements Service<Address> {

    @Resource(name = "addressDao")
    private Dao<Address> addressDao;

    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public Long insert(Address address) {
        return addressDao.insert(address);
    }

    @Override
    public Long update(Address address, Long id) {
        return addressDao.update(address,id);
    }

    @Override
    public void deleteById(Long id) {
          addressDao.deleteById(id);
    }
}
