package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.PdfResumeDao;
import ua.softserve.ita.dao.SkillDao;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.model.PdfResume;
import ua.softserve.ita.model.Skill;
import ua.softserve.ita.utility.QueryUtility;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PdfResumeDaoImpl extends AbstractDao<PdfResume,Long> implements PdfResumeDao {

    private static final String NAME = "name";


    @Override
    public void deleteAll() {
        sessionFactory.getCurrentSession().createQuery("delete from PdfResume").executeUpdate();
    }

    @Override
    public Optional<CV> findByPdfName(String name) {
        return QueryUtility.findOrEmpty(() -> {
            CV result = null;
            try {
                result = (CV) createNamedQuery(CV.FIND_BY_PDF_NAME)
                        .setParameter(NAME, name)
                        .getSingleResult();
            } catch (NoResultException ex) {
                Logger.getLogger(CompanyDaoImpl.class.getName()).log(Level.WARNING, "PdfFile not found with name " + name);
            }
            return result;
        });
    }
}
