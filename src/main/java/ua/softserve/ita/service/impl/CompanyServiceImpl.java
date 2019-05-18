package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.*;
import ua.softserve.ita.dto.CompanyDTO.CompanyPaginationDTO;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.CompanyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyDao companyDao;
    private final AddressDao addressDao;
    private final ContactDao contactDao;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao, AddressDao addressDao, ContactDao contactDao, UserDao userDao,
                              RoleDao roleDao) {
        this.companyDao = companyDao;
        this.addressDao = addressDao;
        this.contactDao = contactDao;
        this.userDao = userDao;
        this.roleDao = roleDao;
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

        User loggedUser = userDao.findById(getLoggedUser().get().getUserID()).get();
        company.setUser(loggedUser);

        Optional<Company> com = companyDao.findByName(company.getName());
        Company result = null;

        if(!com.isPresent()) {
            addressDao.save(company.getAddress());
            contactDao.save(company.getContact());
            result = companyDao.save(company);

            User user = result.getUser();

            if(user.getRoles().stream().noneMatch(role -> role.getType().equals("cowner"))) {
                user.getRoles().add(roleDao.findByType("cowner"));

                userDao.update(user);
            }
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

    @Override
    public List<Company> findByUserId(Long id) {
        return companyDao.findByUserId(id);
    }
}
