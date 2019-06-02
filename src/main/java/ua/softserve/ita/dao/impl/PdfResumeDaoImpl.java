package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.PdfResumeDao;
import ua.softserve.ita.model.PdfResume;
import ua.softserve.ita.utility.QueryUtility;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PdfResumeDaoImpl extends AbstractDao<PdfResume, Long> implements PdfResumeDao {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public void deleteAll() {
        sessionFactory.getCurrentSession().createQuery("delete from PdfResume").executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<PdfResume> findByUserId(Long id) {
        return QueryUtility.findOrEmpty(() -> {
            PdfResume result = null;
            try {
                result = (PdfResume) createNamedQuery(PdfResume.FIND_BY_USER_ID)
                        .setParameter(ID, id)
                        .getSingleResult();
            } catch (NoResultException ex) {
                Logger.getLogger(PdfResumeDaoImpl.class.getName()).log(Level.WARNING, "PdfResume not found with id " + id);
            }

            return result;
        });
    }

    @Override
    public Optional<PdfResume> findByPdfName(String name) {
        return QueryUtility.findOrEmpty(() -> {
            PdfResume result = null;
            try {
                result = (PdfResume) createNamedQuery(PdfResume.FIND_BY_PDF_NAME)
                        .setParameter(NAME, name)
                        .getSingleResult();
            } catch (NoResultException ex) {
                Logger.getLogger(PdfResumeDaoImpl.class.getName()).log(Level.WARNING, "PdfFile not found with name " + name);
            }

            return result;
        });
    }

}
