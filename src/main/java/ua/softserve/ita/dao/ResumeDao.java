package ua.softserve.ita.dao;

import ua.softserve.ita.model.Resume;

import java.util.Optional;

public interface ResumeDao extends BaseDao<Resume,Long> {

    Optional<Resume> findByUserId(Long id);
}
