package ua.com.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.dao.impl.search.SearchVacancyDao;
import ua.com.dto.search.SearchRequestDto;
import ua.com.dto.search.SearchVacancyResponseDto;
import ua.com.model.*;
import ua.com.model.enumtype.Status;
import ua.com.model.profile.Address;
import ua.com.model.profile.Contact;
import ua.com.model.profile.Person;
import ua.com.model.profile.Photo;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchVacancyDaoTest {

    private SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(Photo.class)
                .addAnnotatedClass(Resume.class)
                .addAnnotatedClass(Job.class)
                .addAnnotatedClass(Skill.class)
                .addAnnotatedClass(Education.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(Claim.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Requirement.class)
                .addAnnotatedClass(Status.class)
                .addAnnotatedClass(Vacancy.class)
                .addAnnotatedClass(VerificationToken.class)
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/rabotyNET")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "postgres")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .buildSessionFactory();
    }

    @Test
    void getResponse() {
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        searchRequestDto.setSearchParameter("position");
        searchRequestDto.setSearchText("Java ");
        searchRequestDto.setFirstResultNumber(0);
        searchRequestDto.setResultsOnPage(5000);
        searchRequestDto.setDirection("asc");
        searchRequestDto.setSearchSort("position");
        SearchVacancyDao searchVacancyDao = new SearchVacancyDao(sessionFactory);
        SearchVacancyResponseDto searchVacancyResponseDto = searchVacancyDao.getResponse(searchRequestDto);
        assertEquals(searchVacancyResponseDto.getCount().intValue(), searchVacancyResponseDto.getSearchVacancyDtos().size());
    }

}
