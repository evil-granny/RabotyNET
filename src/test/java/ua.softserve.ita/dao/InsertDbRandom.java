package ua.softserve.ita.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.softserve.ita.model.*;
import ua.softserve.ita.model.enumtype.Employment;
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
    private String[] languages = {"Java", "C#", "C++", "Python", "Angular", "JavaScript", "Fortran", "HTML/CSS", "Scala"};
    private String[] ranks = {"Junior", "Middle", "Senior"};
    private String[] positions = {"Developer", "QATC"};
    private String[] companies = {"Google", "Meta Cortex", "Microsoft", "Apple", "Amazon", "USA Government", "IBM",
            "Tesla", "GMC", "Cyberdyne Systems", "Umbrella", "Omni Consumer Products"};
    private String[] universities = {"Stanford University", "Massachusetts Institute of Technology",
            "Harvard University", "Princeton University", "University of Chicago"};
    private List<Employment> employmentList = new ArrayList<>();
    private int next = 0;
    private Random random = new Random();

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

        employmentList = Arrays.asList(Employment.values());
    }

    LocalDate getLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    User getUser() {
        User user = new User();
        user.setLogin((random.nextInt(70000) + 10000) + "@gmail.com");
        user.setPassword("password");
        return user;
    }

    Address getAddress(long id) {
        Address address = new Address();
        address.setAddressId(id);
        address.setCountry("USA");
        address.setCity(cityList.get(random.nextInt(cityList.size())));
        return address;
    }

    Contact getContact(long id) {
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

    Education getEducation(long id) {
        Education education = new Education();
        education.setDegree("Master");
        education.setGraduation(5);
        education.setEducationId(id);
        education.setSchool(universities[random.nextInt(universities.length)]);
        education.setSpecialty("Computer science");
        return education;
    }

    Set<Skill> getSkills(CV cv) {
        Skill skill1 = new Skill();
        skill1.setTitle(languages[random.nextInt(languages.length)]);
        skill1.setDescription("Core");
        skill1.setCv(cv);
        Skill skill2 = new Skill();
        skill2.setTitle(languages[random.nextInt(languages.length)]);
        if (skill2.getTitle().equals(skill1.getTitle())) {
            skill2.setTitle(languages[random.nextInt(languages.length)]);
        }
        skill2.setDescription("Core");
        skill2.setCv(cv);
        Set<Skill> skills = new HashSet<>();
        skills.add(skill1);
        skills.add(skill2);
        return skills;
    }

    Set<Job> getJobs(CV cv) {
        Job job = new Job();
        job.setBegin(LocalDate.parse("2005-02-02"));
        job.setEnd(LocalDate.parse("2012-03-03"));
        job.setPosition(positions[random.nextInt(positions.length)]);
        job.setCompanyName(companies[random.nextInt(companies.length)]);
        job.setCv(cv);
        Set<Job> jobs = new HashSet<>();
        jobs.add(job);
        return jobs;
    }

    CV getCv(long user_id, Education education, Person person) {
        CV cv = new CV();
        cv.setPosition(ranks[random.nextInt(ranks.length)] + " " +
                languages[random.nextInt(languages.length)] + " " +
                positions[random.nextInt(positions.length)]);
        cv.setEducation(education);
        cv.setCvId(user_id);
        cv.setPerson(person);
        return cv;
    }

    Company getCompany(Contact contact, Address address, User user) {
        Company company = new Company();
            company.setEdrpou(String.format("%08d", random.nextInt(100000000)));
            company.setName(companies[next++]);
            if(company.getName().equals("Meta Cortex")){
                company.setDescription("Wake up.. The Matrix has you...");
            }
            company.setWebsite(company.getName().replace(" ", "") + ".com");
            company.setContact(contact);
            company.setAddress(address);
            company.setUser(user);
            return company;
    }

    Vacancy getVacancy(Company company) {
        Vacancy vacancy = new Vacancy();
        vacancy.setPosition(ranks[random.nextInt(ranks.length)] + " " +
                languages[random.nextInt(languages.length)] + " " +
                positions[random.nextInt(positions.length)]);
        vacancy.setEmployment(employmentList.get(random.nextInt(employmentList.size())));
        vacancy.setSalary(random.nextInt(5) * 1000 + 500);
        vacancy.setCompany(company);
        return vacancy;
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

    void insertCvs(int count, Session session) {
        for (int i = 1; i <= count; i++) {
            session.beginTransaction();
            User user = getUser();
            session.save(user);
            Address address = getAddress(user.getUserId());
            session.save(address);
            Contact contact = getContact(user.getUserId());
            contact.setEmail(user.getLogin());
            session.save(contact);
            Education education = getEducation(user.getUserId());
            session.save(education);
            Person person = getPerson(user.getUserId(), address, contact);
            session.save(person);
            CV cv = getCv(user.getUserId(), education, person);
                session.save(cv);
                log.info("#: " + String.valueOf(i) + " - CV Id = " + String.valueOf(cv.getCvId()));
            Set<Job> jobs = getJobs(cv);
            for (Job job : jobs) {
                session.save(job);
            }
            Set<Skill> skills = getSkills(cv);
            for (Skill skill : skills) {
                session.save(skill);
            }
            log.info("#: " + String.valueOf(i) + " - " + person.getFirstName() + " " + person.getLastName());
            session.getTransaction().commit();
        }
    }

    void insertVacancies(int count, Session session) {
        for (int i = 1; i <= count; i++) {
            session.beginTransaction();
            User user = getUser();
            session.save(user);
            Contact contact = getContact(user.getUserId());
            session.save(contact);
            Address address = getAddress(user.getUserId());
            session.save(address);
            Company company = getCompany(contact, address, user);
            session.save(company);
            for (int j = 0; j < 8; j++) {
                Vacancy vacancy = getVacancy(company);
                session.save(vacancy);
            }
            session.getTransaction().commit();
        }
    }

    @Test
    void insert() throws FileNotFoundException {
        setData();
        Session session = sessionFactory.openSession();
        insertCvs(1000, session);
        insertVacancies(12, session);
        session.close();
    }

}
