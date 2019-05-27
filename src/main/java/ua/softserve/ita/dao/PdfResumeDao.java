package ua.softserve.ita.dao;

import ua.softserve.ita.model.CV;
import ua.softserve.ita.model.PdfResume;

import java.util.Optional;

public interface PdfResumeDao extends BaseDao<PdfResume,Long> {

    void deleteAll();

    public Optional<CV> findByPdfName(String name);

}
