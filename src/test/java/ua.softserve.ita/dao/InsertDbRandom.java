package ua.softserve.ita.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.softserve.ita.model.*;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.model.profile.Person;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
class InsertDbRandom {

    private SessionFactory sessionFactory;

    private List<String> nameList = new ArrayList<>();
    private List<String> lastNameList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private List<CV> cvList = new ArrayList<>();
    private Person person;


    void setData() throws FileNotFoundException {
        Scanner nameScanner =
                new Scanner(new File("src\\test\\resources\\names.txt")).useDelimiter("\\s");
        Scanner lastNameScanner =
                new Scanner(new File("src\\test\\resources\\last_names.txt")).useDelimiter("\\s");
        Scanner cityScanner =
                new Scanner(new File("src\\test\\resources\\cities.txt")).useDelimiter(",");

        while (nameScanner.hasNext()) {
            nameList.add(nameScanner.next());
        }

        while (lastNameScanner.hasNext()) {
            lastNameList.add(lastNameScanner.next());
        }

        while (cityScanner.hasNext()) {
            cityList.add(cityScanner.next().trim());
        }
    }

    LocalDate getLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    void getPerson() {
        Random random = new Random();
        person = new Person();
        person.setFirstName(nameList.get(random.nextInt(nameList.size())));
        person.setLastName(lastNameList.get(random.nextInt(lastNameList.size())));
        person.setBirthday(getLocalDate());
        person.setPhoto("photo");

        Contact contact = new Contact();
        contact.setContactId(person.getUserId());
        contact.setPhoneNumber("+380" + String.format("%09d", random.nextInt(1000000000)));
        contact.setEmail(person.getFirstName() + person.getLastName() + "@gmail.com");
        person.setContact(contact);

        User user = new User();
        user.setEnabled(true);
        user.setLogin(contact.getEmail());
        user.setPassword("password");
        person.setUserId(user.getUserId());
        person.setUser(user);

        Address address = new Address();
        address.setAddressId(person.getUserId());
        address.setCountry("USA");
        address.setCity(cityList.get(random.nextInt(cityList.size())));
        person.setAddress(address);

//        getCvList();

    }

    void getCvList(){
        CV cv = new CV();
        cv.setCvId(person.getUserId());
        cv.setPosition("Junior Java Developer");

        Education education = new Education();
        education.setDegree("Master");
        education.setGraduation(5);
        education.setEducationId(cv.getCvId());
        education.setSchool("University of Michigan");
        education.setSpecialty("Computer science");
        education.setEducationId(cv.getCvId());
        cv.setEducation(education);

        Job job = new Job();
        job.setBegin(LocalDate.parse("2005-02-02"));
        job.setEnd(LocalDate.parse("2012-03-03"));
        job.setPosition("Developer");
        job.setCompanyName("Google");
        Set<Job> jobs = new HashSet<>();
        jobs.add(job);
        cv.setJobs(jobs);

        Skill skill1 = new Skill();
        skill1.setTitle("Java");
        skill1.setDescription("Core");
        Set<Skill> skills = new HashSet<>();
        skills.add(skill1);
        cv.setSkills(skills);

        cvList.add(cv);
    }

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
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .buildSessionFactory();
    }

    @Test
    void insert() throws FileNotFoundException {
        setData();
        Session session = sessionFactory.openSession();
        for (int i = 1; i <= 1000; i++) {
            session.beginTransaction();
            getPerson();
            session.save(person);
            for(CV cv : cvList) {
                session.save(cv);
            }
            session.getTransaction().commit();
            log.info("#: " + String.valueOf(i) + " - " + person.getFirstName() + " " + person.getLastName());
        }
        session.close();
    }

}
