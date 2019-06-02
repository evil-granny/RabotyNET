package ua.softserve.ita.service;

import ua.softserve.ita.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    Optional<Skill> findById(Long id);

    List<Skill> findAll();

    Skill save(Skill skill);

    Skill update(Skill skill);

    void deleteById(Long id);

}
