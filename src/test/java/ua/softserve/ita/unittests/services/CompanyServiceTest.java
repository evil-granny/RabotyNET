package ua.softserve.ita.unittests.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.softserve.ita.dao.CompanyDao;
import ua.softserve.ita.dao.RoleDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dto.CompanyDTO.CompanyPaginationDTO;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.UserPrincipal;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.service.impl.CompanyServiceImpl;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.utility.LoggedUserUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggedUserUtil.class})
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
        CompanyPaginationDTO mockDTO = new CompanyPaginationDTO(ALL_COUNT, mockCompanies);

        when(companyDao.findWithPagination(eq(FIRST), eq(COUNT))).thenReturn(mockCompanies);
        when(companyDao.getCompaniesCount()).thenReturn(ALL_COUNT);
        CompanyPaginationDTO companyPaginationDTO = service.findAllWithPagination(FIRST, COUNT);

        assertEquals(mockDTO, companyPaginationDTO);

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
}
