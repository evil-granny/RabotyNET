package ua.softserve.ita.dao;

import ua.softserve.ita.model.CV;

public interface CVDao extends BaseDao<CV,Long> {

    CV findByUserId(Long id);
}
