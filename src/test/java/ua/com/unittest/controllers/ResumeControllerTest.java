package ua.com.unittest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.com.controller.ResumeController;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.Resume;
import ua.com.model.UserPrincipal;
import ua.com.service.JobService;
import ua.com.service.ResumeService;
import ua.com.service.SkillService;
import ua.com.utility.LoggedUserUtil;
import ua.com.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggedUserUtil.class)
@PowerMockIgnore("javax.management.*")
public class ResumeControllerTest {

    @Mock
    private ResumeService resumeService;

    @Mock
    private JobService jobService;

    @Mock
    private SkillService skillService;

    private ResumeController controller;

    private static final long ID = 1;
    private static final long USER_ID = 2;
    private static final long VACANCY_ID = 2;

    @Before
    public void setUp() {
        this.controller = new ResumeController(resumeService, skillService, jobService);
    }

    @Test
    public void getResumeById()  {
        Resume mockResume = new Resume();

        when(resumeService.findById(eq(ID))).thenReturn(Optional.of(mockResume));

        Resume resumeById = controller.findById(ID);

        assertEquals(mockResume, resumeById);

        verify(resumeService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(resumeService);
    }

    @Test(expected = ResourceNotFoundException.class )
    public void getProductsResumeServiceThrowsException()  {
        when(resumeService.findById(eq(ID))).thenThrow(new ResourceNotFoundException("Resume not found with id "));
        controller.findById(ID);
        verify(resumeService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(resumeService);
    }

    @Test
    public void getAllResume(){
        List<Resume> mockResumes = new ArrayList<>();
        when(resumeService.findAll()).thenReturn(mockResumes);

        List<Resume> allResumes = controller.findAll();

        assertEquals(mockResumes, allResumes);

        verify(resumeService, times(1)).findAll();
        verifyNoMoreInteractions(resumeService);
    }

    @Test
    public void getResumeByUserId(){
        Resume mockResume = new Resume();

        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("user.user@gmail.com","user", new ArrayList<>(), USER_ID)));
        when(resumeService.findByUserId(eq(USER_ID))).thenReturn(Optional.of(mockResume));

        Resume resumeByUserId = controller.getResumeByUserId(LoggedUserUtil.getLoggedUser().get().getUserId());

        assertEquals(mockResume,resumeByUserId);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(resumeService, times(1)).findByUserId(eq(USER_ID));
        verifyNoMoreInteractions(resumeService);
    }

    @Test(expected = ResourceNotFoundException.class )
    public void getProductsResumeByUserIdServiceThrowsException()  {
        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("user.user@gmail.com","user", new ArrayList<>(), USER_ID)));
        when(resumeService.findByUserId(eq(USER_ID))).thenThrow(new ResourceNotFoundException("Resume not found with id "));
        controller.getResumeByUserId(LoggedUserUtil.getLoggedUser().get().getUserId());

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(resumeService, times(1)).findByUserId(eq(USER_ID));
        verifyNoMoreInteractions(resumeService);
    }

    @Test
    public void createResume(){
        Resume mockResume = Resume.builder()
                .resumeId(4L)
                .position("Developer")
                .build();

        when(resumeService.save(any(Resume.class))).thenReturn(mockResume);
        Resume createdResume = controller.insert(new Resume());

        assertEquals(mockResume, createdResume);

        verify(resumeService, times(1)).save(any(Resume.class));
        verifyNoMoreInteractions(resumeService);
    }

    @Test
    public void updateResume(){
        Resume mockResume = Resume.builder()
                .resumeId(4L)
                .position("Developer")
                .build();

        when(resumeService.update(any(Resume.class))).thenReturn(mockResume);
        Resume createdResume = controller.update(new Resume());

        assertEquals(mockResume, createdResume);

        verify(resumeService, times(1)).update(any(Resume.class));
        verifyNoMoreInteractions(resumeService);
    }

    @Test
    public void deleteResume(){
        controller.deleteById(ID);

        verify(resumeService, times(1)).deleteById(eq(ID));
        verifyNoMoreInteractions(resumeService);
    }

    @Test
    public void deleteSkillInResume(){
        controller.deleteSkill(ID);

        verify(skillService, times(1)).deleteById(eq(ID));
        verifyNoMoreInteractions(skillService);
    }

    @Test
    public void deleteJobInResume(){
        controller.deleteJob(ID);

        verify(jobService, times(1)).deleteById(eq(ID));
        verifyNoMoreInteractions(jobService);
    }

    @Test
    public void getResumeByVacancyId(){
        List<Resume> mockResumes = new ArrayList<>();

        when(resumeService.findResumeByVacancyId(eq(VACANCY_ID))).thenReturn(mockResumes);

        List<Resume> allResumeByVacancyId = controller.getResumeByVacancyId(VACANCY_ID);

        assertEquals(mockResumes,allResumeByVacancyId);

        verify(resumeService, times(1)).findResumeByVacancyId(eq(USER_ID));
        verifyNoMoreInteractions(resumeService);
    }
}
