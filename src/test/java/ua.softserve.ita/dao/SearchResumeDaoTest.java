package ua.softserve.ita.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.softserve.ita.model.*;
import ua.softserve.ita.model.enumtype.Employment;
import ua.softserve.ita.model.enumtype.Status;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.model.profile.Photo;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class SearchResumeDaoTest {

    private SessionFactory sessionFactory;

    private List<String> nameList = new ArrayList<>();
    private List<String> lastNameList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private String[] languages =
            {"Java", "Python", "Angular", "JavaScript", "Fortran", "HTML", "CSS", "Scala", "Assembler"};
    private String[] ranks = {"Junior", "Middle", "Senior"};
    private String[] positions = {"Developer", "QATC"};
    private String[] companies = {"Meta Cortex", "Google", "Microsoft", "Apple", "Amazon", "USA Government", "IBM",
            "Tesla", "GMC", "Cyberdyne Systems", "Umbrella", "Omni Consumer Products"};
    private String[] universities = {"Stanford University", "Massachusetts Institute of Technology",
            "Harvard University", "Princeton University", "University of Chicago"};
    private List<Employment> employmentList = new ArrayList<>();
    private List<Status> statusList = new ArrayList<>();
    private int next = 0;
    private Random random = new Random();

    private void setData() throws FileNotFoundException {
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
        statusList = Arrays.asList(Status.values());
    }

    private LocalDate getLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private User getUser() {
        User user = new User();
        user.setLogin((random.nextInt(70000) + 10000) + "@gmail.com");
        user.setPassword("password");
        return user;
    }

    private Address getAddress(long id) {
        Address address = new Address();
        address.setAddressId(id);
        address.setCountry("USA");
        address.setCity(cityList.get(random.nextInt(cityList.size())));
        return address;
    }

    private Contact getContact(long id) {
        Contact contact = new Contact();
        contact.setContactId(id);
        contact.setPhoneNumber("+380" + String.format("%09d", random.nextInt(1000000000)));
        return contact;
    }

    private Photo getPhoto(long id) {
        Photo photo = new Photo();
        photo.setPhotoId(id);
        return photo;
    }

    private Person getPerson(long id, Address address, Contact contact, Photo photo) {
        Person person = new Person();
        person.setUserId(id);
        person.setFirstName(nameList.get(random.nextInt(nameList.size())));
        person.setLastName(lastNameList.get(random.nextInt(lastNameList.size())));
        person.setBirthday(getLocalDate());
        person.setPhoto(photo);
        person.setContact(contact);
        person.setAddress(address);
        return person;
    }

    private Education getEducation(long id) {
        Education education = new Education();
        education.setDegree("Master");
        education.setGraduation(2010);
        education.setEducationId(id);
        education.setSchool(universities[random.nextInt(universities.length)]);
        education.setSpecialty("Computer science");
        return education;
    }

    private Set<Skill> getSkills(Resume resume) {
        Skill skill1 = new Skill();
        skill1.setTitle(languages[random.nextInt(languages.length)]);
        skill1.setDescription("Core");
        skill1.setResume(resume);
        skill1.setPrintPdf(true);
        Skill skill2 = new Skill();
        skill2.setTitle(languages[random.nextInt(languages.length)]);
        if (skill2.getTitle().equals(skill1.getTitle())) {
            skill2.setTitle(languages[random.nextInt(languages.length)]);
        }
        skill2.setDescription("Core");
        skill2.setResume(resume);
        skill2.setPrintPdf(true);
        Set<Skill> skills = new HashSet<>();
        skills.add(skill1);
        skills.add(skill2);
        return skills;
    }

    private Set<Job> getJobs(Resume resume) {
        Job job = new Job();
        job.setBegin(LocalDate.parse("2005-02-02"));
        job.setEnd(LocalDate.parse("2012-03-03"));
        job.setPosition(positions[random.nextInt(positions.length)]);
        job.setCompanyName(companies[random.nextInt(companies.length)]);
        job.setResume(resume);
        job.setPrintPdf(true);
        Set<Job> jobs = new HashSet<>();
        jobs.add(job);
        return jobs;
    }

    private Resume getCv(long user_id, Education education, Person person) {
        Resume resume = new Resume();
        resume.setPosition(ranks[random.nextInt(ranks.length)] + " " +
                languages[random.nextInt(languages.length)] + " " +
                positions[random.nextInt(positions.length)]);
        resume.setEducation(education);
        resume.setResumeId(user_id);
        resume.setPerson(person);
        return resume;
    }

    private Company getCompany(Contact contact, Address address, User user) {
        Company company = new Company();
        company.setEdrpou(String.format("%08d", random.nextInt(100000000)));
        company.setName(companies[next++]);
        company.setStatus(statusList.get(random.nextInt(statusList.size())));
        if (company.getName().equals("Meta Cortex")) {
            company.setDescription("Wake up.. The Matrix has you...");
        }
        company.setWebsite(company.getName().replace(" ", "") + ".com");
        company.setContact(contact);
        company.setAddress(address);
        company.setUser(user);
        return company;
    }

    private Vacancy getVacancy(Company company) {
        Vacancy vacancy = new Vacancy();
        vacancy.setDescription("Loking for good worker");
        vacancy.setPosition(ranks[random.nextInt(ranks.length)] + " " +
                languages[random.nextInt(languages.length)] + " " +
                positions[random.nextInt(positions.length)]);
        vacancy.setEmployment(employmentList.get(random.nextInt(employmentList.size())));
        vacancy.setSalary(random.nextInt(5) * 1000 + 500);
        vacancy.setCompany(company);
        return vacancy;
    }

    private void insertRegisteredUsers(Session session) {

        session.beginTransaction();

        User adminUser = new User();
        adminUser.setLogin("admin@gmail.com");
        adminUser.setPassword("$2a$10$E2.PwtnpF2p6aB3NFM3Qo.TarTYsaiWD0yTZ7qY1U3K.ybKxNvCku");
        adminUser.setEnabled(true);
        session.save(adminUser);
        Role adminRole = new Role();
        adminRole.setType("admin");
        adminRole.setRoleId(adminUser.getUserId());
        session.save(adminRole);
        List<Role> adminRoleList = new ArrayList<>();
        adminRoleList.add(adminRole);
        adminUser.setRoles(adminRoleList);
        session.update(adminUser);

//        User userUser = new User();
//        userUser.setLogin("user@gmail.com");
//        userUser.setPassword("$2a$10$t31PsVNWl8eaWr9/gPwKKeX.4Q2grl12wmiRrN9fEZDMlMGHwA92m");
//        userUser.setEnabled(true);
//        session.save(userUser);
//        Role userRole = new Role();
//        userRole.setType("user");
//        userRole.setRoleId(userUser.getUserId());
//        session.save(userRole);
//        List<Role> userRoleList = new ArrayList<>();
//        userRoleList.add(userRole);
//        userUser.setRoles(userRoleList);
//        session.update(userUser);

//        User cownerUser = new User();
//        cownerUser.setLogin("cowner@gmail.com");
//        cownerUser.setPassword("$2a$10$DmeWO6UlY/m2QjJaxLGUzezqOotvJmpzbBmZGBr8o/HHeNUuCWcpK");
//        cownerUser.setEnabled(true);
//        session.save(cownerUser);
//        Role cownerRole = new Role();
//        cownerRole.setType("cowner");
//        cownerRole.setRoleId(cownerUser.getUserId());
//        session.save(cownerRole);
//        List<Role> cownerRoleList = new ArrayList<>();
//        cownerRoleList.add(cownerRole);
//        cownerUser.setRoles(cownerRoleList);
//        session.update(cownerUser);

        session.getTransaction().commit();
    }

    private void insertCvs(int count, Session session) {
        for (int i = 1; i <= count; i++) {
            session.beginTransaction();

            User user = new User();
            if (i == 1) {
                user.setLogin("user@gmail.com");
            } else {
                user.setLogin("user" + i + "@gmail.com");
            }
            user.setPassword("$2a$10$t31PsVNWl8eaWr9/gPwKKeX.4Q2grl12wmiRrN9fEZDMlMGHwA92m");
            user.setEnabled(true);
            session.save(user);
            Role userRole = new Role();
            userRole.setType("user");
            userRole.setRoleId(user.getUserId());
            session.save(userRole);
            List<Role> userRoleList = new ArrayList<>();
            userRoleList.add(userRole);
            user.setRoles(userRoleList);
            session.update(user);

            Address address = getAddress(user.getUserId());
            session.save(address);
            Contact contact = getContact(user.getUserId());
            contact.setEmail(user.getLogin());
            session.save(contact);
            Education education = getEducation(user.getUserId());
            session.save(education);
            Photo photo = getPhoto(user.getUserId());
            session.save(photo);
            Person person = getPerson(user.getUserId(), address, contact, photo);
            session.save(person);
            Resume resume = getCv(user.getUserId(), education, person);
            session.save(resume);
            log.info("#: " + String.valueOf(i) + " - Resume Id = " + String.valueOf(resume.getResumeId()));
            Set<Job> jobs = getJobs(resume);
            for (Job job : jobs) {
                session.save(job);
            }
            Set<Skill> skills = getSkills(resume);
            for (Skill skill : skills) {
                session.save(skill);
            }
            log.info("#: " + String.valueOf(i) + " - " + person.getFirstName() + " " + person.getLastName());
            session.getTransaction().commit();
        }
    }

    private void insertVacancies(int count, Session session) {
        for (int i = 1; i <= count; i++) {
            session.beginTransaction();
            User cownerUser = new User();
            if (i == 1) {
                cownerUser.setLogin("cowner@gmail.com");
            } else {
                cownerUser.setLogin("cowner" + i + "@gmail.com");
            }
            cownerUser.setPassword("$2a$10$DmeWO6UlY/m2QjJaxLGUzezqOotvJmpzbBmZGBr8o/HHeNUuCWcpK");
            cownerUser.setEnabled(true);
            session.save(cownerUser);
            Role cownerRole = new Role();
            cownerRole.setType("cowner");
            cownerRole.setRoleId(cownerUser.getUserId());
            session.save(cownerRole);
            List<Role> cownerRoleList = new ArrayList<>();
            cownerRoleList.add(cownerRole);
            cownerUser.setRoles(cownerRoleList);
            session.update(cownerUser);
            Contact contact = getContact(cownerUser.getUserId());
            contact.setEmail(cownerUser.getLogin());
            session.save(contact);
            Address address = getAddress(cownerUser.getUserId());
            session.save(address);
            Company company = getCompany(contact, address, cownerUser);
            session.save(company);
            for (int j = 0; j < 8; j++) {
                Vacancy vacancy = getVacancy(company);
                if(j == 2 || j == 4){
                    vacancy.setHotVacancy(true);
                }
                session.save(vacancy);
            }
            Photo photo = getPhoto(cownerUser.getUserId());
            session.save(photo);
            Person person = getPerson(cownerUser.getUserId(), address, contact, photo);
            session.save(person);

            session.getTransaction().commit();
        }
    }

    private void insert() throws FileNotFoundException {
        setData();
        Session session = sessionFactory.openSession();
        insertCvs(10, session);
        insertVacancies(12, session);
        insertRegisteredUsers(session);
    }

    @BeforeEach
    void setUp() throws FileNotFoundException {
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
        insert();
    }

    @Test
    void search() {
//        SearchResumeDao searchResumeDao= new SearchResumeDao(sessionFactory);
//        SearchResumeResponseDTO searchResumeResponseDTO = searchResumeDao.getResponse("name", "jo", 5000, 0);
//        assertEquals(searchResumeResponseDTO.getCount().intValue(), searchResumeResponseDTO.getSearchResumeDTOS().size());
    }


}

