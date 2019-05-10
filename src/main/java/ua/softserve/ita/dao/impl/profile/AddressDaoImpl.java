package ua.softserve.ita.dao.impl.profile;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.AddressDao;
import ua.softserve.ita.dao.impl.AbstractDao;
import ua.softserve.ita.model.profile.Address;

@Repository
public class AddressDaoImpl extends AbstractDao<Address, Long> implements AddressDao {
}
