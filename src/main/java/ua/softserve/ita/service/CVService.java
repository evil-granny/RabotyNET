package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.CV;

import javax.annotation.Resource;
import java.util.List;

@Component("cvService")
@org.springframework.stereotype.Service
@Transactional
public class CVService implements Service<CV> {

    @Resource(name = "cvDao")
    private Dao<CV> cvDao;

    @Override
    public CV findById(Long id) {
        return cvDao.findById(id);
    }

    @Override
    public List<CV> findAll() {
        return cvDao.findAll();
    }

    @Override
    public CV create(CV cv) {
        return cvDao.create(cv);
    }

    @Override
    public CV update(CV cv, Long id) {
        return cvDao.update(cv, id);
    }

    @Override
    public void deleteById(Long id) {
        cvDao.deleteById(id);
    }

}
