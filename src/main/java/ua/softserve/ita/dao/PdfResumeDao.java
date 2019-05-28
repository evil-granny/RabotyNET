package ua.softserve.ita.dao;

import ua.softserve.ita.model.CV;
import ua.softserve.ita.model.PdfResume;

import java.util.Optional;

public interface PdfResumeDao extends BaseDao<PdfResume,Long> {

    void deleteAll();

    Optional<PdfResume> findByUserId(Long id);

    public Optional<PdfResume> findByPdfName(String name);

}
