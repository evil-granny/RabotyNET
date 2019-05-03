package ua.softserve.ita.service.profile;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.service.Service;

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
    public Address create(Address address) {
        return addressDao.create(address);
    }

    @Override
    public Address update(Address address) {
        return addressDao.update(address);
    }

    @Override
    public void deleteById(Long id) {
        addressDao.deleteById(id);
    }

}
