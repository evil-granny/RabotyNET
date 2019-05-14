package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.AddressDao;
import ua.softserve.ita.dao.CompanyDao;
import ua.softserve.ita.dao.ContactDao;
import ua.softserve.ita.dto.CompanyDTO.CompanyPaginationDTO;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.service.CompanyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyDao companyDao;
    private final AddressDao addressDao;
    private final ContactDao contactDao;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao, AddressDao addressDao, ContactDao contactDao) {
        this.companyDao = companyDao;
        this.addressDao = addressDao;
        this.contactDao = contactDao;
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyDao.findById(id);
    }

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public CompanyPaginationDTO findAllWithPagination(int first, int count) {
        return new CompanyPaginationDTO(companyDao.getCompaniesCount(), companyDao.findWithPagination(first, count));
    }

    @Override
    public Optional<Company> save(Company company) {
        Optional<Company> com = companyDao.findByName(company.getName());
        Company result = null;

        if(!com.isPresent()) {
            addressDao.save(company.getAddress());
            contactDao.save(company.getContact());
            result = companyDao.save(company);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Company update(Company company) {
        companyDao.update(company);
        addressDao.update(company.getAddress());
        contactDao.update(company.getContact());
        return companyDao.update(company);
    }

    @Override
    public void deleteById(Long id) {
        companyDao.deleteById(id);
    }

    @Override
    public Optional<Company> findByName(String name) {
        return companyDao.findByName(name);
    }
}
