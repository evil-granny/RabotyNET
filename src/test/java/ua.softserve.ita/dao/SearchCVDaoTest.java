package ua.softserve.ita.dao;
import com.fasterxml.classmate.AnnotationConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.*;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;
import static org.hibernate.cfg.AvailableSettings.C3P0_MAX_STATEMENTS;

class SearchCVDaoTest {

    private SessionFactory sessionFactory;

    @BeforeEach
    void getSessionFactory() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(CV.class)
                .addAnnotatedClass(Job.class)
                .addAnnotatedClass(Skill.class)
                .addAnnotatedClass(Education.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Role.class)
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
    void fillDB(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long id = 1;
        Person person = Person.builder()
                .firstName("John")
                .lastName("Golt")
                .userId(id)
                .build();
        session.save(person);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void searchByName() {
    }

    @Test
    void searchByPhone() {
    }

    @Test
    void searchByCity() {
    }

    @Test
    void searchBySkill() {
    }

    @Test
    void searchByAll() {
    }

}
