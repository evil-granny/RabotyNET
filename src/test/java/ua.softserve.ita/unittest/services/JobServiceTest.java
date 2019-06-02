package ua.softserve.ita.unittest.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.softserve.ita.dao.JobDao;
import ua.softserve.ita.model.Job;
import ua.softserve.ita.service.JobService;
import ua.softserve.ita.service.impl.JobServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class JobServiceTest {
    
    @Mock
    private JobDao jobDao;
    
    private JobService service;
    
    private final static long ID = 2;
    
    @Before
    public void setUp(){
        this.service = new JobServiceImpl(jobDao);
    }

    @Test
    public void getSkillById(){
        Job mockJob = Job.builder()
                .jobId(ID)
                .build();

        when(jobDao.findById(eq(ID))).thenReturn(Optional.of(mockJob));

        Optional<Job> jobById = service.findById(ID);

        assertEquals(Optional.of(mockJob), jobById);

        verify(jobDao, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(jobDao);
    }

    @Test
    public void getAllSkill(){
        List<Job> mockJobs = new ArrayList<>();
        when(jobDao.findAll()).thenReturn(mockJobs);

        List<Job> allJobs = service.findAll();

        assertEquals(mockJobs, allJobs);

        verify(jobDao, times(1)).findAll();
        verifyNoMoreInteractions(jobDao);
    }

    @Test
    public void createSkill(){
        Job mockJob = Job.builder()
                .jobId(4L)
                .build();

        when(jobDao.save(any(Job.class))).thenReturn(mockJob);
        Job createdJob = service.save(new Job());

        assertEquals(mockJob, createdJob);

        verify(jobDao, times(1)).save(any(Job.class));
        verifyNoMoreInteractions(jobDao);
    }

    @Test
    public void updateSkill(){
        Job mockJob = Job.builder()
                .jobId(4L)
                .build();

        when(jobDao.update(any(Job.class))).thenReturn(mockJob);
        Job createdJob = service.update(new Job());

        assertEquals(mockJob, createdJob);

        verify(jobDao, times(1)).update(any(Job.class));
        verifyNoMoreInteractions(jobDao);
    }

    @Test
    public void deleteSkill(){
        service.deleteById(ID);

        verify(jobDao, times(1)).deleteById(eq(ID));
        verifyNoMoreInteractions(jobDao);
    }
}
