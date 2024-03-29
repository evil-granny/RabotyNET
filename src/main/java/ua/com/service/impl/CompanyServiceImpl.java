package ua.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ua.com.dao.CompanyDao;
import ua.com.dao.RoleDao;
import ua.com.dao.UserDao;
import ua.com.dto.company.CompanyPaginationDto;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.Company;
import ua.com.model.User;
import ua.com.model.enumtype.Status;
import ua.com.service.CompanyService;
import ua.com.service.letter.GenerateLetter;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.com.utility.LoggedUserUtil.getLoggedUser;

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
        return companyDao.findAll().stream()
                .sorted(Comparator.comparing(Company::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyPaginationDto findAllWithPagination(int first, int count) {
        return new CompanyPaginationDto(companyDao.getCompaniesCount(), companyDao.findWithPagination(first, count).stream()
                .sorted(Comparator.comparing(Company::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList()));
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
        if (getLoggedUser().isPresent()) {
            Collection<GrantedAuthority> authorities = getLoggedUser().get().getAuthorities();
            Stream<String> stringStream = authorities.stream()
                    .map(GrantedAuthority::getAuthority);
            Long loggedUserId = getLoggedUser().get().getUserId();
            if (company.getUser().getUserId().equals(loggedUserId) ||
                    stringStream.findFirst().get().equals("ROLE_ADMIN")) {
                return companyDao.update(company);
            }
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
        Optional<Company> result = companyDao.findByName(company.getName());
        Company com = result.orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + company.getName()));

        letterService.sendCompanyApprove(com, hostLink +
                "/approveCompany/" + com.getName() + "/" + Objects.hash(com.getName()));
        com.setStatus(Status.MAIL_SENT);
        companyDao.update(com);
        return result;
    }

    @Override
    public Optional<Company> approve(Company company, String companyToken) {
        if (!companyToken.equals(Objects.hash(company.getName()) + "")) {
            return Optional.empty();
        }

        Optional<Company> result = companyDao.findByName(company.getName());
        Company com = result.orElseThrow(() -> new ResourceNotFoundException("Company not found with name " + company.getName()));

        if (com.getUser().getUserId().equals(getLoggedUser().get().getUserId())) {
            com.setStatus(Status.APPROVED);
            User user = com.getUser();
            if (user.getRoles().stream().noneMatch(role -> role.getType().equals("cowner"))) {
                user.getRoles().add(roleDao.findByType("cowner"));
                userDao.update(user);
            }
            companyDao.update(com);
        }

        return result;
    }

    @Override
    public Optional<Company> findCompanyByVacancyId(Long id) {
        return companyDao.findByVacancyId(id);
    }

}
