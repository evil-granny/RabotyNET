package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Status;

import javax.annotation.Resource;
import java.util.List;

@Component("statusService")
@org.springframework.stereotype.Service
@Transactional
public class StatusService implements Service<Status> {

    @Resource(name = "statusDao")
    private Dao<Status> statusDao;

    @Override
    public Status findById(Long id) {
        return statusDao.findById(id);
    }

    @Override
    public List<Status> findAll() {
        return statusDao.findAll();
    }

    @Override
    public Status create(Status status) {
        return statusDao.create(status);
    }

    @Override
    public Status update(Status status) {
        return statusDao.update(status);
    }

    @Override
    public void deleteById(Long id) {
        statusDao.deleteById(id);
    }
}
