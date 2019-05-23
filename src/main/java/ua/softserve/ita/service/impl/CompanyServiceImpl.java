package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.*;
import ua.softserve.ita.dto.CompanyDTO.CompanyPaginationDTO;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.enumtype.Status;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.letter.GenerateLetter;

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
    private final GenerateLetter letterService;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao, AddressDao addressDao, ContactDao contactDao, UserDao userDao,
                              RoleDao roleDao, GenerateLetter letterService) {
        this.companyDao = companyDao;
        this.addressDao = addressDao;
        this.contactDao = contactDao;
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.letterService = letterService;
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
        User loggedUser = userDao.findById(getLoggedUser().get().getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        company.setUser(loggedUser);
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
        if(company.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
            companyDao.update(company);
            addressDao.update(company.getAddress());
            contactDao.update(company.getContact());
        }
        return companyDao.update(company);
    }

    @Override
    public void deleteById(Long id) {
        Company company = companyDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Company with id: %d not found", id)));
        if(company.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
            companyDao.deleteById(id);
        }
    }

    @Override
    public Optional<Company> findByName(String name) {
        return companyDao.findByName(name);
    }

    @Override
    public List<Company> findByUserId(Long id) {
        return companyDao.findByUserId(id);
    }

    @Override
    public Optional<Company> sendMail(Company company, String hostLink) {
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 10000, 5);

        Optional<Company> res = companyDao.findByName(company.getName());

        if(res.isPresent()) {
            company = res.get();

            letterService.sendCompanyApprove(company, hostLink +
                    "/approveCompany/" + company.getName() + "/" + encoder.encode(company.getName()));
            company.setStatus(Status.MAIL_SENT);

            companyDao.update(company);
        }

        return res;
    }

    @Override
    public Optional<Company> approve(Company company, String companyToken) {
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 10000, 5);

        if(!encoder.matches(company.getName(), companyToken))
            return Optional.empty();

        Optional<Company> res = companyDao.findByName(company.getName());

        if(res.isPresent()) {
            company = res.get();

            if (company.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
                company.setStatus(Status.APPROVED);

                User user = company.getUser();

                if (user.getRoles().stream().noneMatch(role -> role.getType().equals("cowner"))) {
                    user.getRoles().add(roleDao.findByType("cowner"));

                    userDao.update(user);
                }
                companyDao.update(company);
            }
        }

        return res;
    }
}
