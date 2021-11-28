package ua.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.dao.UserDao;
import ua.com.dao.VacancyDao;
import ua.com.dto.VacancyDto;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.User;
import ua.com.model.Vacancy;
import ua.com.service.vacancy.BookmarkService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookmarkServiceImpl implements BookmarkService {

    private static final int COUNT_VACANCIES_ON_SINGLE_PAGE = 9;

    private final UserDao userDao;
    private final VacancyDao vacancyDao;

    @Autowired
    public BookmarkServiceImpl(UserDao userDao, VacancyDao vacancyDao) {
        this.userDao = userDao;
        this.vacancyDao = vacancyDao;
    }

    @Override
    public Optional<Vacancy> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public VacancyDto findAllBookmarksByUserId(Long userId, int first) {
        List<Vacancy> bookmarks = vacancyDao.findAllByUserIdWithPagination(userId, first, COUNT_VACANCIES_ON_SINGLE_PAGE);
        bookmarks.forEach(bookmark -> bookmark.setMarkedAsBookmark(true));

        return new VacancyDto(vacancyDao.getCountOfVacanciesByUserId(userId),
                bookmarks);
    }

    @Override
    public void save(Long vacancyId, Long userId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d was not found!", userId)));

        Vacancy vacancy = vacancyDao.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", vacancyId)));

        user.getVacancies().add(vacancy);
        vacancy.getUsers().add(user);
        vacancyDao.save(vacancy);

        userDao.update(user);
    }

    @Override
    public void deleteBookmarkByUserIdAndVacancyId(Long userId, Long vacancyId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d was not found!", userId)));

        Vacancy vacancy = vacancyDao.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", vacancyId)));

        user.getVacancies().removeIf(vacancy::equals);
        vacancy.getUsers().removeIf(user::equals);

        vacancyDao.update(vacancy);
        userDao.update(user);
    }

}
