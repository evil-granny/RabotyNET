package ua.softserve.ita.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.softserve.ita.dao.impl.search.SearchVacancyDao;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;
import ua.softserve.ita.model.*;
import ua.softserve.ita.model.enumtype.Status;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.model.profile.Photo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
                .setProperty("hibernate.connection.password", "root")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .buildSessionFactory();
    }

    @Test
    void getResponse() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setSearchParameter("position");
        searchRequestDTO.setSearchText("Java ");
        searchRequestDTO.setFirstResultNumber(0);
        searchRequestDTO.setResultsOnPage(5000);
        searchRequestDTO.setDirection("asc");
        searchRequestDTO.setSearchSort("position");
        SearchVacancyDao searchVacancyDao = new SearchVacancyDao(sessionFactory);
        SearchVacancyResponseDTO searchVacancyResponseDTO = searchVacancyDao.getResponse(searchRequestDTO);
        assertEquals(searchVacancyResponseDTO.getCount().intValue(), searchVacancyResponseDTO.getSearchVacancyDTOS().size());
    }
}
