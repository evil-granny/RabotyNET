package ua.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.dao.RequirementDao;
import ua.com.dao.VacancyDao;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.Requirement;
import ua.com.model.Vacancy;
import ua.com.service.RequirementService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequirementServiceImpl implements RequirementService {

    private final RequirementDao requirementDao;
    private final VacancyDao vacancyDao;

    @Autowired
    public RequirementServiceImpl(RequirementDao requirementDao, VacancyDao vacancyDao) {
        this.requirementDao = requirementDao;
        this.vacancyDao = vacancyDao;
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
        Vacancy vacancy = vacancyDao.findByRequirementId(requirement.getRequirementId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found"));
        requirement.setVacancy(vacancy);

        return requirementDao.update(requirement);
    }

    @Override
    public void deleteById(Long id) {
        requirementDao.deleteById(id);
    }

}
