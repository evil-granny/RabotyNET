package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.CompanyDao;
import ua.softserve.ita.dao.RoleDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dto.company.CompanyPaginationDto;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.enumtype.Status;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.letter.GenerateLetter;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;
    private final UserDao userDao;
    private final RoleDao roleDao;

    private final GenerateLetter letterService;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao, UserDao userDao, RoleDao roleDao, GenerateLetter letterService) {
        this.companyDao = companyDao;
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
    public CompanyPaginationDto findAllWithPagination(int first, int count) {
        return new CompanyPaginationDto(companyDao.getCompaniesCount(), companyDao.findWithPagination(first, count));
    }

    @Override
    public Optional<Company> save(Company company) {
        User loggedUser = userDao.findById(getLoggedUser().get().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        company.setUser(loggedUser);
        Optional<Company> com = companyDao.findByName(company.getName());
        Company result = null;

        if (!com.isPresent()) {
            result = companyDao.save(company);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Company update(Company company) {
        if (company.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
            return companyDao.update(company);
        }

        return company;
    }

    @Override
    public void deleteById(Long id) {
        Company company = companyDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Company with id: %d not found", id)));

        if (company.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
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
        Optional<Company> res = companyDao.findByName(company.getName());

        Company com = res.orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + company.getName()));

        letterService.sendCompanyApprove(com, hostLink +
                "/approveCompany/" + com.getName() + "/" + Objects.hash(com.getName()));
        com.setStatus(Status.MAIL_SENT);
        companyDao.update(com);

        return res;
    }

    @Override
    public Optional<Company> approve(Company company, String companyToken) {
        if (!companyToken.equals(Objects.hash(company.getName()) + ""))
            return Optional.empty();

        Optional<Company> res = companyDao.findByName(company.getName());

        Company com = res.orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + company.getName()));

        if (com.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
            com.setStatus(Status.APPROVED);

            User user = com.getUser();

            if (user.getRoles().stream().noneMatch(role -> role.getType().equals("cowner"))) {
                user.getRoles().add(roleDao.findByType("cowner"));

                userDao.update(user);
            }
            companyDao.update(com);
        }

        return res;
    }

    @Override
    public Optional<Company> findCompanyByVacancyId(Long id) {
        return companyDao.findByVacancyId(id);
    }

}
