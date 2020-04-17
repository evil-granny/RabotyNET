package ua.com.dao;

import ua.com.model.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeDao extends BaseDao<Resume, Long> {

    Optional<Resume> findByUserId(Long id);

    List<Resume> findResumeByVacancyId(Long vacancyId);

}
