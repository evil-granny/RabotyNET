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
    Random random = new Random();

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

    User getUser() {
        User user = new User();
        user.setLogin("login" + random.nextInt() + "@gmail.com");
        user.setPassword("password");
        return user;
    }

    Address getAddress(long id){
        Address address = new Address();
        address.setAddressId(id);
        address.setCountry("USA");
        address.setCity(cityList.get(random.nextInt(cityList.size())));
        return address;
    }

    Contact getContact(long id){
        Contact contact = new Contact();
        contact.setContactId(id);
        contact.setPhoneNumber("+380" + String.format("%09d", random.nextInt(1000000000)));
        return contact;
    }

    Person getPerson(long id, Address address, Contact contact) {
        Person person = new Person();
        person.setUserId(id);
        person.setFirstName(nameList.get(random.nextInt(nameList.size())));
        person.setLastName(lastNameList.get(random.nextInt(lastNameList.size())));
        person.setBirthday(getLocalDate());
        person.setPhoto("photo");
        person.setContact(contact);
        person.setAddress(address);
        return person;
    }

    Education getEducation(Person person){
        Education education = new Education();
        education.setDegree("Master");
        education.setGraduation(5);
        education.setEducationId(person.getUserId());
        education.setSchool("University of Michigan");
        education.setSpecialty("Computer science");
        return education;
    }

    Set<Skill> getSkills(CV cv){
        Skill skill1 = new Skill();
        skill1.setTitle("Java");
        skill1.setDescription("Core");
        skill1.setCv(cv);
        Set<Skill> skills = new HashSet<>();
        skills.add(skill1);
        return skills;
    }

    Set<Job> getJobs(CV cv){
        Job job = new Job();
        job.setBegin(LocalDate.parse("2005-02-02"));
        job.setEnd(LocalDate.parse("2012-03-03"));
        job.setPosition("Developer");
        job.setCompanyName("Google");
        job.setCv(cv);
        Set<Job> jobs = new HashSet<>();
        jobs.add(job);
        return jobs;
    }

    List<CV> getCvList(Person person, Education education) {
        CV cv = new CV();
        cv.setPosition("Junior Java Developer");
        cv.setPerson(person);
        cv.setEducation(education);
        cvList.add(cv);
        return cvList;
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
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .buildSessionFactory();
    }

    @Test
    void insert() throws FileNotFoundException {
        setData();
        Session session = sessionFactory.openSession();
        for (int i = 1; i <= 10; i++) {
            session.beginTransaction();
            User user = getUser();
            session.save(user);
            Address address = getAddress(user.getUserId());
            session.save(address);
            Contact contact = getContact(user.getUserId());
            session.save(contact);
            Person person = getPerson(user.getUserId(), address, contact);
            session.save(person);
            Education education = getEducation(person);
            session.save(education);
            List<CV> cvList = getCvList(person, education);
            session.save(cvList.get(0));
            Set<Job> jobs = getJobs(cvList.get(0));
            for (Job job : jobs){
                session.save(job);
            }
            Set<Skill> skills = getSkills(cvList.get(0));
            for (Skill skill : skills){
                session.save(skill);
            }
            log.info("#: " + String.valueOf(i) + " - " + person.getFirstName() + " " + person.getLastName());
            session.getTransaction().commit();
        }
        session.close();
    }

}
