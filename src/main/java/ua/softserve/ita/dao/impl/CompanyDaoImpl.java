package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.CompanyDao;
import ua.softserve.ita.model.Company;

@Repository
public class CompanyDaoImpl extends AbstractDao<Company, Long> implements CompanyDao {

}
