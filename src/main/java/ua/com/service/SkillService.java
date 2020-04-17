package ua.com.service;

import ua.com.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    Optional<Skill> findById(Long id);

    List<Skill> findAll();

    Skill save(Skill skill);

    Skill update(Skill skill);

    void deleteById(Long id);

}
