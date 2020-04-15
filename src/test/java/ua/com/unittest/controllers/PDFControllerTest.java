package ua.com.unittest.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.com.controller.PDFController;
import ua.com.model.Resume;
import ua.com.service.PdfResumeService;
import ua.com.service.ResumeService;
import ua.com.service.VacancyService;
import ua.com.service.letter.GenerateLetter;
import ua.com.service.pdfcreator.CreateResumePdf;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class PDFControllerTest {

    @Mock
    private ResumeService resumeService;

    @Mock
    private GenerateLetter generateService;

    @Mock
    private CreateResumePdf pdfService;

    @Mock
    private PdfResumeService pdfResumeService;

    @Mock
    private VacancyService vacancyService;

    private PDFController controller;

    private static final long ID = 1;

    @Before
    public void setUp() {
        this.controller = new PDFController(resumeService, generateService, pdfService, pdfResumeService, vacancyService);
    }

    @Test
    public void getResumeById() {
        Resume mockResume = new Resume();
        when(resumeService.findById(eq(ID))).thenReturn(Optional.of(mockResume));
        Resume resumeById = controller.getCV(ID);
        assertEquals(mockResume, resumeById);
        verify(resumeService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(resumeService);
    }

    @Test
    public void updateResume() {
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

}
