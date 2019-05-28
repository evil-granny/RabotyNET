package ua.softserve.ita.mocktests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.ita.controller.ResumeController;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Resume;
import ua.softserve.ita.service.*;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResumeControllerTest {

    @Mock
    private ResumeService resumeService;

    @Mock
    private JobService jobService;

    @Mock
    private SkillService skillService;

    private ResumeController controller;

    private static final long ID = 1;

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
        verifyNoMoreInteractions(resumeService, skillService, jobService);
    }

    @Test(expected = ResourceNotFoundException.class )
    public void getProductsResumeServiceThrowsException()  {
        when(resumeService.findById(eq(ID))).thenThrow(new ResourceNotFoundException("Resume not found with id "));
        controller.findById(ID);
        verify(resumeService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(resumeService, skillService, jobService);
    }

}