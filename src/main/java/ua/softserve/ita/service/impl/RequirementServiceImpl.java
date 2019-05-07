package ua.softserve.ita.service.impl;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.RequirementDao;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.service.RequirementService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequirementServiceImpl implements RequirementService {

    private final RequirementDao requirementDao;
    private final SessionFactory sessionFactory;

    @Autowired
    public RequirementServiceImpl(RequirementDao requirementDao, SessionFactory sessionFactory) {
        this.requirementDao = requirementDao;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Requirement> findById(Long id) {
        return requirementDao.findById(id);
    }

    @Override
    public List<Requirement> findAll() {
        return requirementDao.findAll();
    }

    @Override
    public Requirement save(Requirement requirement) {
        return requirementDao.save(requirement);
    }

    @Override
    public Requirement update(Requirement requirement) {
        Query query = (Query) sessionFactory.createEntityManager().createNativeQuery
                ("SELECT * FROM vacancy WHERE vacancy_id = (SELECT requirement.vacancy_id FROM requirement WHERE requirement_id = :id)", Vacancy.class);
        query.setParameter("id", requirement.getRequirementId());
        Vacancy vacancy = (Vacancy) query.getSingleResult();
        requirement.setVacancy(vacancy);
        return requirementDao.update(requirement);
    }

    @Override
    public void deleteById(Long id) {
        requirementDao.deleteById(id);
    }
}
