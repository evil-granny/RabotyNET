package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Company;

import javax.annotation.Resource;
import java.util.List;

@Component("companyService")
@org.springframework.stereotype.Service
@Transactional
public class CompanyService implements Service<Company> {

    @Resource(name = "companyDao")
    private Dao<Company> companyDao;

    @Override
    public Company findById(Long id) {
        return companyDao.findById(id);
    }

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public Company insert(Company company) {
        return companyDao.insert(company);
    }

    @Override
    public Company update(Company company) {
        return companyDao.update(company);
    }

    @Override
    public void deleteById(Long id) {
      companyDao.deleteById(id);
    }
}
