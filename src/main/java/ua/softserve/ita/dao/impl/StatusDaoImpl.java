package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.StatusDao;
import ua.softserve.ita.model.Status;

@Repository
public class StatusDaoImpl extends AbstractDao<Status,Long> implements StatusDao {
}
