package ua.softserve.ita.dao;

import ua.softserve.ita.model.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeDao extends BaseDao<Resume, Long> {

    Optional<Resume> findByUserId(Long id);

    List<Resume> findResumeByVacancyId(Long vacancyId);

}
