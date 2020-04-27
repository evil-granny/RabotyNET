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
                .setProperty("hibernate.connection.url", "jdbc:postgresql://ec2-54-217-213-79.eu-west-1.compute.amazonaws.com:5432/d9k5eoh0ih48gq")
                .setProperty("hibernate.connection.username", "oxmxyadlvfjbdj")
                .setProperty("hibernate.connection.password", "5e268c712194df109e7750926345f479f362b47973f9e59bdae2cb826f7f24ff")
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
