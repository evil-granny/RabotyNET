package ua.softserve.ita.dao;

import ua.softserve.ita.model.CV;

import java.util.Optional;

public interface CVDao extends BaseDao<CV,Long> {

    Optional<CV> findByUserId(Long id);
}
