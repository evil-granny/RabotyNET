package ua.com.unittest.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.com.dao.SkillDao;
import ua.com.service.SkillService;
import ua.com.service.impl.SkillServiceImpl;
import ua.com.model.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class SkillServiceTest {

    @Mock
    private SkillDao skillDao;

    private SkillService service;

    private static final long ID = 2;

    @Before
    public void setUp(){
        this.service = new SkillServiceImpl(skillDao);
    }

    @Test
    public void getSkillById(){
        Skill mockSkill = Skill.builder()
                .skillId(ID)
                .build();

        when(skillDao.findById(eq(ID))).thenReturn(Optional.of(mockSkill));

        Optional<Skill> skillById = service.findById(ID);

        assertEquals(Optional.of(mockSkill), skillById);

        verify(skillDao, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(skillDao);
    }

    @Test
    public void getAllSkill(){
        List<Skill> mockSkills = new ArrayList<>();
        when(skillDao.findAll()).thenReturn(mockSkills);

        List<Skill> allSkills = service.findAll();

        assertEquals(mockSkills, allSkills);

        verify(skillDao, times(1)).findAll();
        verifyNoMoreInteractions(skillDao);
    }

    @Test
    public void createSkill(){
        Skill mockSkill = Skill.builder()
                .skillId(4L)
                .build();

        when(skillDao.save(any(Skill.class))).thenReturn(mockSkill);
        Skill createdSkill = service.save(new Skill());

        assertEquals(mockSkill, createdSkill);

        verify(skillDao, times(1)).save(any(Skill.class));
        verifyNoMoreInteractions(skillDao);
    }

    @Test
    public void updateSkill(){
        Skill mockSkill = Skill.builder()
                .skillId(4L)
                .build();

        when(skillDao.update(any(Skill.class))).thenReturn(mockSkill);
        Skill createdSkill = service.update(new Skill());

        assertEquals(mockSkill, createdSkill);

        verify(skillDao, times(1)).update(any(Skill.class));
        verifyNoMoreInteractions(skillDao);
    }

    @Test
    public void deleteSkill(){
        service.deleteById(ID);

        verify(skillDao, times(1)).deleteById(eq(ID));
        verifyNoMoreInteractions(skillDao);
    }
}
