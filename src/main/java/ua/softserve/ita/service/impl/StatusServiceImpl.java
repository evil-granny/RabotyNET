package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.StatusDao;
import ua.softserve.ita.model.Status;
import ua.softserve.ita.service.StatusService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class StatusServiceImpl implements StatusService {
    private final StatusDao statusDao;

    @Autowired
    public StatusServiceImpl(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public Optional<Status> findById(Long id) {
        return statusDao.findById(id);
    }

    @Override
    public List<Status> findAll() {
        return statusDao.findAll();
    }

    @Override
    public Status save(Status status) {
        return statusDao.save(status);
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
