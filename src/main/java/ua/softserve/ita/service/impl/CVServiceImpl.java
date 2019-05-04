package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.CVDao;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.service.CVService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CVServiceImpl implements CVService {

    private final CVDao cvDao;

    @Autowired
    public CVServiceImpl(CVDao cvDao) {
        this.cvDao = cvDao;
    }

    @Override
    public Optional<CV> findById(Long id) {
        return cvDao.findById(id);
    }

    @Override
    public List<CV> findAll() {
        return cvDao.findAll();
    }

    @Override
    public CV save(CV cv) {
        return cvDao.save(cv);
    }

    @Override
    public CV update(CV cv) {
        return cvDao.update(cv);
    }

    @Override
    public void deleteById(Long id) {
        cvDao.deleteById(id);
    }
}
