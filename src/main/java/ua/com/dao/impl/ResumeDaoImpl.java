package ua.com.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.model.Resume;
import ua.com.utility.QueryUtility;
import ua.com.dao.ResumeDao;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ResumeDaoImpl extends AbstractDao<Resume, Long> implements ResumeDao {

    private static final String ID = "id";

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Resume> findByUserId(Long id) {
        return QueryUtility.findOrEmpty(() -> {
            Resume result = null;
            try {
                result = (Resume) createNamedQuery(Resume.FIND_BY_USER_ID)
                        .setParameter(ID, id)
                        .getSingleResult();
            } catch (NoResultException ex) {
                Logger.getLogger(ResumeDaoImpl.class.getName()).log(Level.WARNING, "Resume not found with id " + id);
            }

            return result;
        });
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Resume> findResumeByVacancyId(Long vacancyId) {
        return (List<Resume>) createNamedQuery(Resume.FIND_RESUME_BY_VACANCY_ID)
                .setParameter(ID, vacancyId)
                .getResultList();
    }

}
