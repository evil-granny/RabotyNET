package ua.softserve.ita.unittest.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.softserve.ita.dao.CompanyDao;
import ua.softserve.ita.dao.RoleDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dto.company.CompanyPaginationDto;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.UserPrincipal;
import ua.softserve.ita.model.enumtype.Status;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.impl.CompanyServiceImpl;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.utility.LoggedUserUtil;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggedUserUtil.class})
@PowerMockIgnore("javax.management.*")
public class CompanyServiceTest {

    @Mock
    private CompanyDao companyDao;

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private GenerateLetter letterService;

    private CompanyService service;

    @Before
    public void setUp() {
        this.service = new CompanyServiceImpl(companyDao, userDao, roleDao, letterService);
    }

    private static final Long COMPANY_ID = 1L;
    private static final int FIRST = 1;
    private static final int COUNT = 2;
    private static final Long ALL_COUNT = 2L;
    private static final Long USER_ID = 1L;
    private static final String COMPANY_NAME = "SoftServe";
    private static final String HOST = "localhost";
    private static final Long VACANCY_ID = 1L;

    @Test
    public void getCompanyById() {
        Company mockCompany = Company.builder()
                .companyId(COMPANY_ID)
                .name("SoftServe")
                .build();

        when(companyDao.findById(eq(COMPANY_ID))).thenReturn(Optional.of(mockCompany));
        Optional<Company> companyByName = service.findById(COMPANY_ID);

        assertEquals(Optional.of(mockCompany), companyByName);

        verify(companyDao, times(1)).findById(eq(COMPANY_ID));
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void getAllCompanies() {
        Company mockCompany1 = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();

        Company mockCompany2 = Company.builder()
                .companyId(2L)
                .name("SharpMinds")
                .build();

        List<Company> mockCompanies = new LinkedList<>();
        mockCompanies.add(mockCompany1);
        mockCompanies.add(mockCompany2);

        when(companyDao.findAll()).thenReturn(mockCompanies);
        List<Company> allCompanies = service.findAll();

        assertEquals(mockCompanies, allCompanies);

        verify(companyDao, times(1)).findAll();
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void getAllCompaniesWithPagination() {
        Company mockCompany1 = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();
        Company mockCompany2 = Company.builder()
                .companyId(2L)
                .name("SharpMinds")
                .build();
        List<Company> mockCompanies = new LinkedList<>();
        mockCompanies.add(mockCompany1);
        mockCompanies.add(mockCompany2);
        CompanyPaginationDto mockDto = new CompanyPaginationDto(ALL_COUNT, mockCompanies);

        when(companyDao.findWithPagination(eq(FIRST), eq(COUNT))).thenReturn(mockCompanies);
        when(companyDao.getCompaniesCount()).thenReturn(ALL_COUNT);
        CompanyPaginationDto companyPaginationDto = service.findAllWithPagination(FIRST, COUNT);

        assertEquals(mockDto, companyPaginationDto);

        verify(companyDao, times(1)).findWithPagination(eq(FIRST), eq(COUNT));
        verify(companyDao, times(1)).getCompaniesCount();
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void createCompany() {
        User mockUser = User.builder()
                .userId(USER_ID)
                .login("admin.admin@gmail.com")
                .password("admin")
                .build();

        Company mockCompany = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal(mockUser.getLogin(), mockUser.getPassword(), new ArrayList<>(), USER_ID)));
        when(userDao.findById(eq(USER_ID))).thenReturn(Optional.of(mockUser));
        when(companyDao.save(any(Company.class))).thenReturn(mockCompany);
        when(companyDao.findByName(eq(COMPANY_NAME))).thenReturn(Optional.empty());
        Optional<Company> createdCompany = service.save(Company.builder().name(COMPANY_NAME).build());

        assertEquals(Optional.of(mockCompany), createdCompany);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(userDao, times(1)).findById(eq(USER_ID));
        verify(companyDao, times(1)).save(any(Company.class));
        verify(companyDao, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(userDao, companyDao);
    }

    @Test
    public void updateCompany() {
        User mockUser = User.builder()
                .userId(USER_ID)
                .build();

        Company mockCompany = Company.builder()
                .companyId(COMPANY_ID)
                .name("SoftServe")
                .user(mockUser)
                .build();

        Company companyToUpdate = Company.builder()
                .companyId(COMPANY_ID)
                .name("SoftServe")
                .user(mockUser)
                .build();

        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("admin", "admin", new ArrayList<>(), USER_ID)));
        when(companyDao.update(eq(companyToUpdate))).thenReturn(mockCompany);
        Company updatedCompany = service.update(companyToUpdate);

        assertEquals(mockCompany, updatedCompany);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(companyDao, times(1)).update(eq(companyToUpdate));
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void deleteCompany() {
        User mockUser = User.builder()
                .userId(USER_ID)
                .build();

        Company mockCompany = Company.builder()
                .companyId(COMPANY_ID)
                .name("SharpMinds")
                .user(mockUser)
                .build();

        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("admin", "admin", new ArrayList<>(), USER_ID)));
        when(companyDao.findById(eq(COMPANY_ID))).thenReturn(Optional.of(mockCompany));

        service.deleteById(COMPANY_ID);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(companyDao, times(1)).findById(eq(COMPANY_ID));
        verify(companyDao, times(1)).deleteById(eq(COMPANY_ID));
        verifyNoMoreInteractions(companyDao);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCompanyNotFound() {
        when(companyDao.findById(eq(COMPANY_ID))).thenThrow(new ResourceNotFoundException(String.format("Company with id: %d not found", COMPANY_ID)));

        service.deleteById(COMPANY_ID);

        verify(companyDao, times(1)).findById(eq(COMPANY_ID));
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void getCompanyByName() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        when(companyDao.findByName(eq(COMPANY_NAME))).thenReturn(Optional.of(mockCompany));
        Optional<Company> companyByName = service.findByName(COMPANY_NAME);

        assertEquals(Optional.of(mockCompany), companyByName);

        verify(companyDao, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void getCompaniesByUserId() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        List<Company> mockCompanies = new LinkedList<>();
        mockCompanies.add(mockCompany);

        when(companyDao.findByUserId(eq(USER_ID))).thenReturn(mockCompanies);
        List<Company> companiesByUserId = service.findByUserId(USER_ID);

        assertEquals(mockCompanies, companiesByUserId);

        verify(companyDao, times(1)).findByUserId(eq(USER_ID));
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void sendMail() {
        Company companySendMailTo = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        Company mockCompany = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        when(companyDao.findByName(eq(COMPANY_NAME))).thenReturn(Optional.of(mockCompany));
        when(companyDao.update(eq(mockCompany))).thenReturn(mockCompany);

        Optional<Company> companyWithSentMail = service.sendMail(companySendMailTo, HOST);

        assertNotEquals(companySendMailTo.getStatus(), companyWithSentMail.get().getStatus());
        companySendMailTo.setStatus(Status.MAIL_SENT);
        assertEquals(Optional.of(companySendMailTo), companyWithSentMail);

        verify(companyDao, times(1)).findByName(COMPANY_NAME);
        verify(companyDao, times(1)).update(eq(mockCompany));
        verify(letterService, times(1)).sendCompanyApprove(eq(mockCompany), any(String.class));
        verifyNoMoreInteractions(companyDao, letterService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void sendMailNotFound() {
        Company companySendMailTo = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        when(companyDao.findByName(eq(COMPANY_NAME))).thenThrow(new ResourceNotFoundException("Company not found with name " + COMPANY_NAME));

        service.sendMail(companySendMailTo, HOST);

        verify(companyDao, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void approveCompany() {
        List<Role> mockRoles = new LinkedList<>();
        mockRoles.add(Role.builder().type("cowner").build());

        User mockUser = User.builder()
                .userId(USER_ID)
                .roles(mockRoles)
                .build();

        Company companyToUpdate = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .user(mockUser)
                .build();

        Company mockCompany = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .user(mockUser)
                .build();

        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("admin", "admin", new ArrayList<>(), USER_ID)));
        when(companyDao.findByName(eq(COMPANY_NAME))).thenReturn(Optional.of(mockCompany));
        when(companyDao.update(eq(mockCompany))).thenReturn(mockCompany);

        Optional<Company> approvedCompany = service.approve(companyToUpdate, Objects.hash(COMPANY_NAME) + "");

        assertNotEquals(companyToUpdate.getStatus(), approvedCompany.get().getStatus());
        companyToUpdate.setStatus(Status.APPROVED);
        assertEquals(Optional.of(companyToUpdate), approvedCompany);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(companyDao, times(1)).findByName(COMPANY_NAME);
        verify(companyDao, times(1)).update(eq(mockCompany));
        verifyNoMoreInteractions(companyDao);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void approveCompanyNotFound() {
        Company companyToApprove = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        when(companyDao.findByName(eq(COMPANY_NAME))).thenThrow(new ResourceNotFoundException("Company not found with name " + COMPANY_NAME));

        service.sendMail(companyToApprove, HOST);

        verify(companyDao, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(companyDao);
    }

    @Test
    public void getCompanyByVacancyId() {
        Company mockCompany = Company.builder()
                .companyId(COMPANY_ID)
                .name("SoftServe")
                .build();

        when(companyDao.findByVacancyId(eq(VACANCY_ID))).thenReturn(Optional.of(mockCompany));
        Optional<Company> companyByVacancyId = service.findCompanyByVacancyId(VACANCY_ID);

        assertEquals(Optional.of(mockCompany), companyByVacancyId);

        verify(companyDao, times(1)).findByVacancyId(eq(VACANCY_ID));
        verifyNoMoreInteractions(companyDao);
    }

}
