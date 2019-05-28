package ua.softserve.ita.unittest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.softserve.ita.controller.VacancyController;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.VacancyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class VacancyControllerTest {

    @Mock
    private VacancyService vacancyService;

    private VacancyController controller;

    private static final long ID = 1;

    @Before
    public void setUp() {
        this.controller = new VacancyController(vacancyService);
    }

    @Test
    public void getVacancyById()  {
        Vacancy mockVacancy = new Vacancy();
        when(vacancyService.findById(eq(ID))).thenReturn(Optional.of(mockVacancy));
        ResponseEntity<Vacancy> vacancyById = controller.getVacancyById(ID);
        assertEquals(mockVacancy, vacancyById);
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

        assertEquals(mockVacancies, allResumes);

        verify(vacancyService, times(1)).findAll();
        verifyNoMoreInteractions(vacancyService);
    }
}
