package ua.softserve.ita.unittest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;
import ua.softserve.ita.controller.VacancyController;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.ResumeService;
import ua.softserve.ita.service.VacancyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(PowerMockRunner.class)
public class VacancyControllerTest {

    @Mock
    private VacancyService vacancyService;

    @Mock
    private ResumeService resumeService;

    private VacancyController controller;

    private static final long ID = 1;

    @Before
    public void setUp() {
        this.controller = new VacancyController(vacancyService, resumeService);
    }

    @Test
    public void getVacancyById()  {
        Vacancy mockVacancy = new Vacancy();
        when(vacancyService.findById(eq(ID))).thenReturn(Optional.of(mockVacancy));
        ResponseEntity<Vacancy> vacancyById = controller.getVacancyById(ID);
        assertEquals(ResponseEntity.ok().body(mockVacancy), vacancyById);
        verify(vacancyService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(vacancyService);
    }

    @Test(expected = ResourceNotFoundException.class )
    public void getProductsVacancyServiceThrowsException()  {
        when(vacancyService.findById(eq(ID))).thenThrow(new ResourceNotFoundException("Vacancy not found with id "));
        controller.getVacancyById(ID);
        verify(vacancyService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(vacancyService);
    }

    @Test
    public void getAllVacancy(){
        List<Vacancy> mockVacancies = new ArrayList<>();
        when(vacancyService.findAll()).thenReturn(mockVacancies);

        ResponseEntity<List<Vacancy>> allResumes = controller.getAllVacancies();

        assertEquals(ResponseEntity.ok().body(mockVacancies), allResumes);

        verify(vacancyService, times(1)).findAll();
        verifyNoMoreInteractions(vacancyService);
    }

    /*@Test
    public void updateResume(){
        Vacancy mockVacancy = Vacancy.builder()
                .vacancyId(4L)
                .position("Developer")
                .build();

        when(vacancyService.update(any(Vacancy.class))).thenReturn(mockVacancy);
        Vacancy createdResume = controllers.updateVacancy(new ResponseEntity<Vacancy>());

        assertEquals(ResponseEntity.ok(mockVacancy), createdResume);

        verify(vacancyService, times(1)).update(any(Vacancy.class));
        verifyNoMoreInteractions(vacancyService);
    }*/

    /*@Test
    public void createResume(){
        Vacancy mockVacancy = Vacancy.builder()
                .vacancyId(4L)
                .position("Developer")
                .build();

        when(vacancyService.save(any(Vacancy.class),eq(ID))).thenReturn(mockVacancy);
        Vacancy createdResume = controllers.createVacancy(new Vacancy(), ID);

        assertEquals(mockVacancy, createdResume);

        verify(vacancyService, times(1)).save(any(Vacancy.class), eq(ID));
        verifyNoMoreInteractions(vacancyService);
    }*/
}
