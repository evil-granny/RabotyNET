package ua.softserve.ita.service;

import ua.softserve.ita.model.PdfResume;

import java.util.List;
import java.util.Optional;

public interface PdfResumeService {

    Optional<PdfResume> findById(Long id);

    List<PdfResume> findAll();

    void save(PdfResume pdfResume);

    PdfResume update(PdfResume pdfResume);

    void deleteById(Long id);

    void deleteAll();

    Optional<PdfResume> findByUserId(Long id);

    Optional<PdfResume> findByPdfName(String name);

}
