package ua.com.dao.impl.profile;

import org.springframework.stereotype.Repository;
import ua.com.dao.AddressDao;
import ua.com.model.profile.Address;
import ua.com.dao.impl.AbstractDao;

@Repository
public class AddressDaoImpl extends AbstractDao<Address, Long> implements AddressDao {
}
