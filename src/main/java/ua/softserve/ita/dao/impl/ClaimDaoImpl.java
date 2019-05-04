package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.ClaimDao;
import ua.softserve.ita.model.Claim;

@Repository
public class ClaimDaoImpl extends AbstractDao<Claim, Long> implements ClaimDao {

}
