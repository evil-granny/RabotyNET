package ua.softserve.ita.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.softserve.ita.dao.impl.search.SearchResumeDao;
import ua.softserve.ita.dto.search.SearchRequestDto;
import ua.softserve.ita.dto.search.SearchResumeResponseDto;
import ua.softserve.ita.model.*;
import ua.softserve.ita.model.enumtype.Currency;
import ua.softserve.ita.model.enumtype.Employment;
import ua.softserve.ita.model.enumtype.Status;
import ua.softserve.ita.model.enumtype.VacancyStatus;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.model.profile.Photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private String[] companies = {"Meta Cortex", "Google", "Microsoft", "Apple", "Amazon", "USA Government", "IBM Tech",
            "Tesla", "Atlantis", "Cyberdyne Systems", "Umbrella", "Omni Consumer Products"};
    private String[] universities = {"Stanford University", "Massachusetts Institute",
            "Harvard University", "Princeton University", "University of Chicago"};
    private List<Employment> employmentList = new ArrayList<>();
    private List<VacancyStatus> vacancyStatusList = new ArrayList<>();
    private List<Currency> currencyList = new ArrayList<>();
    private int next = 0;
    private Random random = new Random();
    private Role userRole, cownerRole;

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
        vacancyStatusList = Arrays.asList(VacancyStatus.values());
        currencyList = Arrays.asList(Currency.values());
    }

    private LocalDate getLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
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

    private Resume getResume(long user_id, Education education, Person person) {
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
        company.setStatus(Status.APPROVED);
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
//        vacancy.setSalary(random.nextInt(5) * 1000 + 500);
        vacancy.setVacancyStatus(vacancyStatusList.get(random.nextInt(vacancyStatusList.size())));
        vacancy.setHotVacancy(random.nextInt(5) % 2 != 0);
//        vacancy.setCurrency(currencyList.get(random.nextInt(currencyList.size())));
        vacancy.setCompany(company);
        return vacancy;
    }

    private void insertRegisteredUsers(Session session) {

        session.beginTransaction();

        Role adminRole = new Role();
        adminRole.setType("admin");
        adminRole.setRoleId(1L);
        session.save(adminRole);

        userRole = new Role();
        userRole.setType("user");
        userRole.setRoleId(2L);
        session.save(userRole);

        cownerRole = new Role();
        cownerRole.setType("cowner");
        cownerRole.setRoleId(3L);
        session.save(cownerRole);

        User adminUser = new User();
        adminUser.setLogin("admin@gmail.com");
        adminUser.setPassword("$2a$10$E2.PwtnpF2p6aB3NFM3Qo.TarTYsaiWD0yTZ7qY1U3K.ybKxNvCku");
        adminUser.setEnabled(true);
        List<Role> adminRoleList = new ArrayList<>();
        adminRoleList.add(adminRole);
        adminUser.setRoles(adminRoleList);
        session.save(adminUser);

        session.getTransaction().commit();
    }

    List<Role> getUserRole() {
        List<Role> userRoleList = new ArrayList<>();
        userRoleList.add(userRole);
        return userRoleList;
    }

    List<Role> getCownerRole() {
        List<Role> cownerRoleList = new ArrayList<>();
        cownerRoleList.add(cownerRole);
        return cownerRoleList;
    }

    private void insertResume(int count, Session session) {
        for (int i = 1; i <= count; i++) {
            session.beginTransaction();

            User user = new User();
            user.setLogin("user" + i + "@gmail.com");
            user.setPassword("$2a$10$t31PsVNWl8eaWr9/gPwKKeX.4Q2grl12wmiRrN9fEZDMlMGHwA92m");
            user.setEnabled(true);
            user.setRoles(getUserRole());
            session.save(user);

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
            Resume resume = getResume(user.getUserId(), education, person);
            session.save(resume);
            log.info("#: " + i + " - Resume Id = " + resume.getResumeId());
            Set<Job> jobs = getJobs(resume);
            for (Job job : jobs) {
                session.save(job);
            }
            Set<Skill> skills = getSkills(resume);
            for (Skill skill : skills) {
                session.save(skill);
            }
            log.info("#: " + i + " - " + person.getFirstName() + " " + person.getLastName());
            session.getTransaction().commit();
        }
    }

    private void insertVacancies(int count, Session session) {
        for (int i = 1; i <= count; i++) {
            session.beginTransaction();
            User cownerUser = new User();
            cownerUser.setLogin("cowner" + i + "@gmail.com");
            cownerUser.setPassword("$2a$10$DmeWO6UlY/m2QjJaxLGUzezqOotvJmpzbBmZGBr8o/HHeNUuCWcpK");
            cownerUser.setEnabled(true);
            cownerUser.setRoles(getCownerRole());
            session.save(cownerUser);
            Contact contact = getContact(cownerUser.getUserId());
            contact.setEmail(cownerUser.getLogin());
            session.save(contact);
            Address address = getAddress(cownerUser.getUserId());
            session.save(address);
            Company company = getCompany(contact, address, cownerUser);
            session.save(company);
            for (int j = 0; j < 8; j++) {
                Vacancy vacancy = getVacancy(company);
                if (j == 2 || j == 4) {
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
        insertRegisteredUsers(session);
        insertVacancies(12, session);
        insertResume(30, session);
    }

    @BeforeEach
    void setUp() throws IOException {
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
//        insert();
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
        SearchResumeDao searchResumeDao = new SearchResumeDao(sessionFactory);
        SearchResumeResponseDto searchResumeResponseDto = searchResumeDao.getResponse(searchRequestDto);
        assertEquals(searchResumeResponseDto.getCount().intValue(), searchResumeResponseDto.getSearchResumeDtos().size());
    }

}
