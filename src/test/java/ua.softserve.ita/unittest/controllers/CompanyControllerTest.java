package ua.softserve.ita.unittest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.softserve.ita.controller.CompanyController;
import ua.softserve.ita.dto.company.CompanyPaginationDto;
import ua.softserve.ita.exception.CompanyAlreadyExistException;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.UserPrincipal;
import ua.softserve.ita.service.CompanyService;
import ua.softserve.ita.utility.LoggedUserUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggedUserUtil.class, CompanyController.class})
@PowerMockIgnore("javax.management.*")
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    private CompanyController controller;

    private static final String COMPANY_NAME = "SoftServe";
    private static final int FIRST = 1;
    private static final int COUNT = 2;
    private static final Long ALL_COUNT = 2L;
    private static final Long USER_ID = 1L;
    private static final Long COMPANY_ID = 1L;
    private static final String TOKEN = "8edfh8";
    private static final Long VACANCY_ID = 3L;

    @Before
    public void setUp() {
        this.controller = new CompanyController(companyService);
    }

    @Test
    public void getCompanyByName() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name(COMPANY_NAME)
                .build();

        when(companyService.findByName(eq(COMPANY_NAME))).thenReturn(Optional.of(mockCompany));
        Company companyByName = controller.getCompanyByName(COMPANY_NAME);

        assertEquals(mockCompany, companyByName);

        verify(companyService, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(companyService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCompanyByNameNotFound() {
        when(companyService.findByName(eq(COMPANY_NAME))).thenThrow(new ResourceNotFoundException("Company not found with name " + COMPANY_NAME));

        controller.getCompanyByName(COMPANY_NAME);

        verify(companyService, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(companyService);
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

        when(companyService.findAll()).thenReturn(mockCompanies);
        List<Company> allCompanies = controller.getAll();

        assertEquals(mockCompanies, allCompanies);

        verify(companyService, times(1)).findAll();
        verifyNoMoreInteractions(companyService);
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

        when(companyService.findAllWithPagination(eq(FIRST), eq(COUNT))).thenReturn(mockDto);
        CompanyPaginationDto companyPaginationDto = controller.getAllWithPagination(FIRST, COUNT);

        assertEquals(mockDto, companyPaginationDto);

        verify(companyService, times(1)).findAllWithPagination(eq(FIRST), eq(COUNT));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void getAllCompaniesByUser() {
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

        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("admin.admin@gmail.com", "admin", new ArrayList<>(), USER_ID)));
        when(companyService.findByUserId(eq(USER_ID))).thenReturn(mockCompanies);
        List<Company> allCompaniesByUser = controller.getAllByUser();

        assertEquals(mockCompanies, allCompaniesByUser);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(companyService, times(1)).findByUserId(eq(USER_ID));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void createCompany() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();

        when(companyService.save(any(Company.class))).thenReturn(Optional.of(mockCompany));
        Company createdCompany = controller.create(new Company());

        assertEquals(mockCompany, createdCompany);

        verify(companyService, times(1)).save(any(Company.class));
        verifyNoMoreInteractions(companyService);
    }

    @Test(expected = CompanyAlreadyExistException.class)
    public void createCompanyAlreadyExists() {
        when(companyService.save(any(Company.class))).thenThrow(new CompanyAlreadyExistException("Company already exists"));
        controller.create(new Company());

        verify(companyService, times(1)).save(any(Company.class));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void updateCompany() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();

        when(companyService.update(any(Company.class))).thenReturn(mockCompany);
        Company updatedCompany = controller.update(new Company());

        assertEquals(mockCompany, updatedCompany);

        verify(companyService, times(1)).update(any(Company.class));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void sendMail() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(companyService.sendMail(any(Company.class), any(String.class))).thenReturn(Optional.of(mockCompany));
        Company companyWithSentLetter = controller.sendMail(new Company(), request);

        assertEquals(mockCompany, companyWithSentLetter);

        verify(companyService, times(1)).sendMail(any(Company.class), any(String.class));
        verifyNoMoreInteractions(companyService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void sendMailThrowsException() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(companyService.sendMail(any(Company.class), any(String.class))).thenThrow(new ResourceNotFoundException("Company not found with name " + COMPANY_NAME));

        controller.sendMail(new Company(), request);

        verify(companyService, times(1)).sendMail(any(Company.class), any(String.class));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void deleteCompany() {
        controller.delete(COMPANY_ID);

        verify(companyService, times(1)).deleteById(eq(COMPANY_ID));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void approveCompany() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();

        when(companyService.approve(any(Company.class), eq(TOKEN))).thenReturn(Optional.of(mockCompany));
        Company approvedCompany = controller.approve(TOKEN, new Company());

        assertEquals(mockCompany, approvedCompany);

        verify(companyService, times(1)).approve(any(Company.class), eq(TOKEN));
        verifyNoMoreInteractions(companyService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void approveCompanyNotFound() {
        when(companyService.approve(any(Company.class), eq(TOKEN))).thenThrow(new ResourceNotFoundException("Company not found with name " + COMPANY_NAME));
        controller.approve(TOKEN, new Company());

        verify(companyService, times(1)).approve(any(Company.class), eq(TOKEN));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void ifCompanyExists() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();

        when(companyService.findByName(eq(COMPANY_NAME))).thenReturn(Optional.of(mockCompany));
        boolean companyExists = controller.exists(COMPANY_NAME);

        assertTrue(companyExists);

        verify(companyService, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void ifCompanyDoesNotExist() {
        when(companyService.findByName(eq(COMPANY_NAME))).thenReturn(Optional.empty());
        boolean companyExists = controller.exists(COMPANY_NAME);

        assertFalse(companyExists);

        verify(companyService, times(1)).findByName(eq(COMPANY_NAME));
        verifyNoMoreInteractions(companyService);
    }

    @Test
    public void getCompanyByVacancyId() {
        Company mockCompany = Company.builder()
                .companyId(1L)
                .name("SoftServe")
                .build();

        when(companyService.findCompanyByVacancyId(eq(VACANCY_ID))).thenReturn(Optional.of(mockCompany));
        Company companyByVacancyId = controller.getCompanyByVacancyId(VACANCY_ID);

        assertEquals(mockCompany, companyByVacancyId);

        verify(companyService, times(1)).findCompanyByVacancyId(eq(VACANCY_ID));
        verifyNoMoreInteractions(companyService);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCompanyByVacancyIdNotFound() {
        when(companyService.findCompanyByVacancyId(eq(VACANCY_ID))).thenThrow(new ResourceNotFoundException("Company not found with vacancy id " + VACANCY_ID));

        controller.getCompanyByVacancyId(VACANCY_ID);

        verify(companyService, times(1)).findCompanyByVacancyId(eq(VACANCY_ID));
        verifyNoMoreInteractions(companyService);
    }

}
