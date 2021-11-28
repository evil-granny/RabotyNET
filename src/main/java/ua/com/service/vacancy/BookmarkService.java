package ua.com.service.vacancy;

import ua.com.dto.VacancyDto;
import ua.com.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface BookmarkService {

    Optional<Vacancy> findById(Long id);

    VacancyDto findAllBookmarksByUserId(Long userId, int first);

    void save(Long vacancyId, Long userId);

    void deleteBookmarkByUserIdAndVacancyId(Long userId, Long vacancyId);

}
