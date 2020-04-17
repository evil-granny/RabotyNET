package ua.com.unittest.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.com.dao.*;
import ua.com.model.Resume;
import ua.com.model.UserPrincipal;
import ua.com.model.profile.Person;
import ua.com.service.ResumeService;
import ua.com.service.impl.ResumeServiceImpl;
import ua.com.service.letter.GenerateLetter;
import ua.com.utility.LoggedUserUtil;
import ua.com.dao.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggedUserUtil.class)
@PowerMockIgnore("javax.management.*")
public class ResumeServiceTest {

    @Mock
    private ResumeDao resumeDao;

    @Mock
    private PersonDao personDao;

    @Mock
    private UserDao userDao;

    @Mock
    private VacancyDao vacancyDao;

    @Mock
    private PdfResumeDao pdfResumeDao;

    @Mock
    private GenerateLetter generateLetter;

    private ResumeService service;

    private static final long ID = 2;
    private static final long USER_ID = 3;
    private static final long VACANCY_ID = 2;

    @Before
    public void setUp(){
        this.service = new ResumeServiceImpl(resumeDao, userDao, personDao, vacancyDao, pdfResumeDao, generateLetter);
    }

    @Test
    public void getResumeById(){
        Resume mockResume = Resume.builder()
                .resumeId(ID)
                .position("Developer")
                .build();
        when(resumeDao.findById(eq(ID))).thenReturn(Optional.of(mockResume));

        Optional<Resume> resumeById = service.findById(ID);

        assertEquals(Optional.of(mockResume), resumeById);

        verify(resumeDao, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(resumeDao);
    }

    @Test
    public void getAllResume(){
        List<Resume> mockResumes = new ArrayList<>();
        when(resumeDao.findAll()).thenReturn(mockResumes);

        List<Resume> allResumes = service.findAll();

        assertEquals(mockResumes, allResumes);

        verify(resumeDao, times(1)).findAll();
        verifyNoMoreInteractions(resumeDao);
    }

    @Test
    public void createResume(){
        Person mockUser = Person.builder()
                .userId(USER_ID)
                .build();

        Resume mockResume = Resume.builder()
                .resumeId(4L)
                .jobs(new HashSet<>())
                .skills(new HashSet<>())
                .position("Developer")
                .person(mockUser)
                .build();

        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("user.user@gmail.com", "user", new ArrayList<>(), USER_ID)));
        when(personDao.findById(eq(USER_ID))).thenReturn(Optional.of(mockUser));
        when(resumeDao.save(any(Resume.class))).thenReturn(mockResume);
        Resume createdResume = service.save(Resume.builder()
                .resumeId(4L)
                .jobs(new HashSet<>())
                .skills(new HashSet<>())
                .position("Developer")
                .person(mockUser)
                .build());

        assertEquals(mockResume, createdResume);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(personDao, times(1)).findById(eq(USER_ID));
        verify(resumeDao, times(1)).save(any(Resume.class));
        verifyNoMoreInteractions(resumeDao, personDao);
    }

    @Test
    public void updateResume(){
        Person mockUser = Person.builder()
                .userId(USER_ID)
                .build();

        Resume mockResume = Resume.builder()
                .resumeId(4L)
                .jobs(new HashSet<>())
                .skills(new HashSet<>())
                .position("Developer")
                .person(mockUser)
                .vacancies(new HashSet<>())
                .build();


        mockStatic(LoggedUserUtil.class);

        when(LoggedUserUtil.getLoggedUser()).thenReturn(Optional.of(new UserPrincipal("user.user@gmail.com", "user", new ArrayList<>(), USER_ID)));
        when(personDao.findById(eq(USER_ID))).thenReturn(Optional.of(mockUser));
        when(resumeDao.update(any(Resume.class))).thenReturn(mockResume);
        Resume createdResume = service.update(Resume.builder()
                .resumeId(4L)
                .jobs(new HashSet<>())
                .skills(new HashSet<>())
                .position("Developer")
                .person(mockUser)
                .vacancies(new HashSet<>())
                .build());

        assertEquals(mockResume, createdResume);

        verifyStatic(LoggedUserUtil.class);
        LoggedUserUtil.getLoggedUser();
        verify(personDao, times(1)).findById(eq(USER_ID));
        verify(resumeDao, times(1)).update(any(Resume.class));
        verifyNoMoreInteractions(resumeDao, personDao);
    }

    @Test
    public void deleteResume(){
        service.deleteById(ID);

        verify(resumeDao, times(1)).deleteById(eq(ID));
        verifyNoMoreInteractions(resumeDao);
    }

    @Test
    public void getResumeByUserId()  {
        Resume mockResume = new Resume();
        when(resumeDao.findByUserId(eq(ID))).thenReturn(Optional.of(mockResume));
        Optional<Resume> resumeById = service.findByUserId(ID);
        assertEquals(Optional.of(mockResume), resumeById);
        verify(resumeDao, times(1)).findByUserId(eq(ID));
        verifyNoMoreInteractions(resumeDao);
    }

}
