package ua.softserve.ita.service;

import ua.softserve.ita.model.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeService {

    Optional<Resume> findById(Long id);

    List<Resume> findAll();

    Resume save(Resume resume);

    Resume update(Resume resume);

    void deleteById(Long id);

    Optional<Resume> findByUserId(Long id);

    List<Resume> findResumeByVacancyId(Long vacancyId);

    Resume sendResumeOnThisVacancy(Resume resume, Long vacancyId);

}
