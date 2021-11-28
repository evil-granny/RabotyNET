--
-- oxmxyadlvfjbdjQL database dump
--

-- Dumped from database version 10.7
-- Dumped by pg_dump version 11.2

-- Started on 2019-04-24 13:17:40

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 43538)
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.address
(
    address_id bigint                NOT NULL,
    apartment  character varying(5),
    building   character varying(5),
    city       character varying(15) NOT NULL,
    country    character varying(20) NOT NULL,
    street     character varying(30),
    zip_code   integer
);


ALTER TABLE public.address
    OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 43536)
-- Name: address_address_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.address_address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.address.address_id;


ALTER TABLE public.address_address_id_seq
    OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 43546)
-- Name: company; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.company
(
    company_id  bigint                NOT NULL,
    name        character varying(30) NOT NULL,
    edrpou      character varying(10) NOT NULL,
    description character varying(255),
    website     character varying(50),
    status      character varying(50) NOT NULL,
    address_id  bigint                NOT NULL,
    contact_id  bigint                NOT NULL,
    photo_id    bigint,
    user_id     bigint                NOT NULL
);


ALTER TABLE public.company
    OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 43544)
-- Name: company_company_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.company_company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.company.company_id;


ALTER TABLE public.company_company_id_seq
    OWNER TO postgres;


--
-- TOC entry 203 (class 1259 OID 43557)
-- Name: contact; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contact
(
    contact_id   bigint NOT NULL,
    email        character varying(50),
    phone_number character varying(255)
);


ALTER TABLE public.contact
    OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 43555)
-- Name: contact_contact_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contact_contact_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.contact.contact_id;


ALTER TABLE public.contact_contact_id_seq
    OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 43565)
-- Name: resume; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resume
(
    resume_id    bigint                NOT NULL,
    photo        character varying(255),
    "position"   character varying(50) NOT NULL,
    education_id bigint                NOT NULL,
    user_id      bigint                NOT NULL
);


ALTER TABLE public.resume
    OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 43563)
-- Name: resume_resume_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.resume_resume_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.resume.resume_id;


ALTER TABLE public.resume_resume_id_seq
    OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 43573)
-- Name: education; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.education
(
    education_id bigint                NOT NULL,
    degree       character varying(30) NOT NULL,
    graduation   integer,
    school       character varying(50) NOT NULL,
    specialty    character varying(100)
);


ALTER TABLE public.education
    OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 43571)
-- Name: education_education_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.education_education_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.education.education_id;


ALTER TABLE public.education_education_id_seq
    OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 43534)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence
    OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 43581)
-- Name: job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.job
(
    job_id       bigint                NOT NULL,
    begin        date,
    company_name character varying(50),
    description  character varying(200),
    "end"        date,
    print_pdf    boolean               NOT NULL,
    "position"   character varying(40) NOT NULL,
    resume_id    bigint                NOT NULL
);


ALTER TABLE public.job
    OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 43579)
-- Name: job_job_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.job_job_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.job.job_id;


ALTER TABLE public.job_job_id_seq
    OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 43587)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person
(
    user_id    bigint NOT NULL,
    birthday   date,
    first_name character varying(20),
    last_name  character varying(20),
    photo      bytea,
    address_id bigint,
    contact_id bigint,
    photo_id   bigint
);


ALTER TABLE public.person
    OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 43597)
-- Name: requirement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.requirement
(
    requirement_id bigint                 NOT NULL,
    description    character varying(100) NOT NULL,
    vacancy_id     bigint                 NOT NULL
);


ALTER TABLE public.requirement
    OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 43595)
-- Name: requirement_requirement_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.requirement_requirement_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.requirement.requirement_id;


ALTER TABLE public.requirement_requirement_id_seq
    OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 43605)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles
(
    role_id bigint                NOT NULL,
    type    character varying(10) NOT NULL
);


ALTER TABLE public.roles
    OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 43603)
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.roles.role_id;


ALTER TABLE public.roles_role_id_seq
    OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 43613)
-- Name: skill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill
(
    skill_id    bigint                NOT NULL,
    description character varying(255),
    title       character varying(30) NOT NULL,
    print_pdf   boolean               NOT NULL,
    resume_id   bigint                NOT NULL
);


ALTER TABLE public.skill
    OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 43611)
-- Name: skill_skill_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.skill_skill_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.skill.skill_id;


ALTER TABLE public.skill_skill_id_seq
    OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 43619)
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_role
    OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 43624)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users
(
    user_id          bigint                 NOT NULL,
    enabled          boolean,
    login            character varying(50)  NOT NULL,
    matchingpassword character varying(255),
    password         character varying(200) NOT NULL
);


ALTER TABLE public.users
    OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 43622)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.users.user_id;


ALTER TABLE public.users_user_id_seq
    OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 43635)
-- Name: vacancy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vacancy
(
    vacancy_id     bigint                NOT NULL,
    description    character varying(60) NOT NULL,
    "position"     character varying(40) NOT NULL,
    employment     character varying(255),
    vacancy_status character varying(255),
    salary         integer,
    currency       character varying(255),
    hot_vacancy    boolean,
    company_id     bigint                NOT NULL
);


ALTER TABLE public.vacancy
    OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 43633)
-- Name: vacancy_vacancy_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vacancy_vacancy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    OWNED BY public.vacancy.vacancy_id;


ALTER TABLE public.vacancy_vacancy_id_seq
    OWNER TO postgres;


--
-- TOC entry 222 (class 1259 OID 43641)
-- Name: verificationtoken; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.verificationtoken
(
    id         bigint NOT NULL,
    expirydate timestamp without time zone,
    token      character varying(255),
    user_id    bigint NOT NULL
);


ALTER TABLE public.verificationtoken
    OWNER TO postgres;

--
-- TOC entry 2105 (class 2604 OID 43541)
-- Name: address address_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ALTER COLUMN address_id SET DEFAULT nextval('public.address_address_id_seq'::regclass);


--
-- TOC entry 2106 (class 2604 OID 43549)
-- Name: company company_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ALTER COLUMN company_id SET DEFAULT nextval('public.company_company_id_seq'::regclass);


--
-- TOC entry 2107 (class 2604 OID 43560)
-- Name: contact contact_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contact
    ALTER COLUMN contact_id SET DEFAULT nextval('public.contact_contact_id_seq'::regclass);


--
-- TOC entry 2108 (class 2604 OID 43568)
-- Name: resume resume_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resume
    ALTER COLUMN resume_id SET DEFAULT nextval('public.resume_resume_id_seq'::regclass);


--
-- TOC entry 2109 (class 2604 OID 43576)
-- Name: education education_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.education
    ALTER COLUMN education_id SET DEFAULT nextval('public.education_education_id_seq'::regclass);


--
-- TOC entry 2110 (class 2604 OID 43584)
-- Name: job job_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job
    ALTER COLUMN job_id SET DEFAULT nextval('public.job_job_id_seq'::regclass);


--
-- TOC entry 2111 (class 2604 OID 43600)
-- Name: requirement requirement_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requirement
    ALTER COLUMN requirement_id SET DEFAULT nextval('public.requirement_requirement_id_seq'::regclass);


--
-- TOC entry 2112 (class 2604 OID 43608)
-- Name: roles role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ALTER COLUMN role_id SET DEFAULT nextval('public.roles_role_id_seq'::regclass);


--
-- TOC entry 2113 (class 2604 OID 43616)
-- Name: skill skill_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
    ALTER COLUMN skill_id SET DEFAULT nextval('public.skill_skill_id_seq'::regclass);


--
-- TOC entry 2114 (class 2604 OID 43627)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 2115 (class 2604 OID 43638)
-- Name: vacancy vacancy_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy
    ALTER COLUMN vacancy_id SET DEFAULT nextval('public.vacancy_vacancy_id_seq'::regclass);




--Insert all data
INSERT INTO public.users(enabled, login, password)
VALUES (true, 'admin@gmail.com', '$2a$10$E2.PwtnpF2p6aB3NFM3Qo.TarTYsaiWD0yTZ7qY1U3K.ybKxNvCku');
INSERT INTO public.users(enabled, login, password)
VALUES (true, 'cowner@gmail.com', '$2a$10$DmeWO6UlY/m2QjJaxLGUzezqOotvJmpzbBmZGBr8o/HHeNUuCWcpK');
INSERT INTO public.users(enabled, login, password)
VALUES (true, 'user@gmail.com', '$2a$10$t31PsVNWl8eaWr9/gPwKKeX.4Q2grl12wmiRrN9fEZDMlMGHwA92m');

INSERT INTO public.roles(role_id, type)
VALUES (1, 'admin');
INSERT INTO public.roles(role_id, type)
VALUES (2, 'cowner');
INSERT INTO public.roles(role_id, type)
VALUES (3, 'user');

INSERT INTO public.user_role(user_id, role_id)
VALUES (1, 1);
INSERT INTO public.user_role(user_id, role_id)
VALUES (2, 2);
INSERT INTO public.user_role(user_id, role_id)
VALUES (2, 3);
INSERT INTO public.user_role(user_id, role_id)
VALUES (3, 3);

INSERT INTO public.address(building, city, country, street, zip_code)
VALUES ('20', 'Chernivtsi', 'Ukraine', 'Holovna', 58000);
INSERT INTO public.address(building, city, country, street, zip_code)
VALUES ('33', 'Chernivtsi', 'Ukraine', 'Shevchenka', 58000);
INSERT INTO public.address(building, city, country, street, zip_code)
VALUES ('246', 'Chernivtsi', 'Ukraine', 'Holovna', 58000);
INSERT INTO public.address(building, city, country, street, zip_code)
VALUES ('33', 'Chernivtsi', 'Ukraine', 'Komarova', 58000);
INSERT INTO public.address(building, city, country, street, zip_code)
VALUES ('15', 'Chernivtsi', 'Ukraine', 'Rivnenska', 58000);

INSERT INTO public.education(degree, graduation, school, specialty)
VALUES ('Master', 2016, 'ChNU', 'Software Engineer');
INSERT INTO public.education(degree, graduation, school, specialty)
VALUES ('Master', 2009, 'KPI', 'Software Engineer');

INSERT INTO public.contact(email, phone_number)
VALUES ('den.ohorodnik@gmail.com', '+380973999060');
INSERT INTO public.contact(email, phone_number)
VALUES ('jon.snow@gmail.com', '+380973219033');
INSERT INTO public.contact(email, phone_number)
VALUES ('softserve.inc@gmail.com', '+380322409999');
INSERT INTO public.contact(email, phone_number)
VALUES ('inventorsoft.inc@gmail.com', '+380984529012');
INSERT INTO public.contact(email, phone_number)
VALUES ('valsoft@gmail.com', '+380972993270');

INSERT INTO public.person(
    user_id)
VALUES (1);
INSERT INTO public.person(user_id, birthday, first_name, last_name, address_id, contact_id, photo_id)
VALUES (2, ' 1986-11-26', 'Jon', 'Snow', 2, 2, null);
INSERT INTO public.person(user_id, birthday, first_name, last_name, address_id, contact_id, photo_id)
VALUES (3, ' 1999-06-04', 'Denys', 'Ohorodnik', 1, 1, null);

INSERT INTO public.resume("position", education_id, user_id)
VALUES ('Junior Developer', 1, 2);
INSERT INTO public.resume("position", education_id, user_id)
VALUES ('Middle Developer', 2, 3);

INSERT INTO public.skill(description, title, print_pdf, resume_id)
VALUES ('Spring MVC', 'MVC', true, 1);
INSERT INTO public.skill(description, title, print_pdf, resume_id)
VALUES ('Spring Boot', 'Spring', true, 1);
INSERT INTO public.skill(description, title, print_pdf, resume_id)
VALUES ('Good skill', 'Linux', true, 2);
INSERT INTO public.skill(description, title, print_pdf, resume_id)
VALUES ('Some experience', 'Angular', true, 2);
INSERT INTO public.skill(description, title, print_pdf, resume_id)
VALUES ('Some experience', 'Html', true, 2);

INSERT INTO public.job(begin, company_name, description, "end", print_pdf, "position", resume_id)
VALUES ('2000-10-10', 'SoftServe', 'Junior Java Developer', '2009-03-04', true, 'Junior', 1);
INSERT INTO public.job(begin, company_name, description, "end", print_pdf, "position", resume_id)
VALUES ('2006-04-08', 'ValSoft', 'Middle Java Developer', '2009-11-04', true, 'Middle', 1);
INSERT INTO public.job(begin, company_name, description, "end", print_pdf, "position", resume_id)
VALUES ('2008-03-08', 'ValSoft', 'Middle Java developer', '2005-07-04', true, 'Middle', 2);
INSERT INTO public.job(begin, company_name, description, "end", print_pdf, "position", resume_id)
VALUES ('2006-10-08', 'SoftServe', 'Junior Java Developer', '2009-10-04', true, 'Junior', 2);
INSERT INTO public.job(begin, company_name, description, "end", print_pdf, "position", resume_id)
VALUES ('2010-04-08', 'InventorSoft', 'Senior Java Developer', '2014-11-04', true, 'Senior', 2);

INSERT INTO public.company(description, edrpou, name, status, website, address_id, contact_id, photo_id, user_id)
VALUES ('We are digital advisors and providers who operate at the cutting edge of technology. We deliver the innovation, quality, and speed that our clients’ users expect.',
        23456742, 'SoftServe', 'APPROVED', 'www.softserveinc.com', 3, 3, null, 2);
INSERT INTO public.company(description, edrpou, name, status, website, address_id, contact_id, photo_id, user_id)
VALUES ('Second company', 55368632, 'InventorSoft', 'CREATED', 'www.inventorsoft.com', 4, 4, null, 2);
INSERT INTO public.company(description, edrpou, name, status, website, address_id, contact_id, photo_id, user_id)
VALUES ('Third company', 63964221, 'ValSoft', 'BLOCKED', 'www.valsoft.com', 5, 5, null, 2);

INSERT INTO public.vacancy(description, "position", salary, currency, employment, vacancy_status, company_id)
VALUES ('Junior Java Developer', 'Junior Developer', 1000, 'USD', 'FULL', 'OCCUPIED', 1);
INSERT INTO public.vacancy(description, "position", salary, currency, employment, vacancy_status, company_id)
VALUES ('Middle React Developer', 'Middle Developer', 3000, 'UAH', 'HOURLY', 'OUTDATED', 1);
INSERT INTO public.vacancy(description, "position", salary, currency, employment, vacancy_status, company_id)
VALUES ('Senior Angular Developer', 'Senior Developer', 4000, 'UAH', 'PART_TIME', 'OUTDATED', 1);
INSERT INTO public.vacancy(description, "position", salary, currency, employment, vacancy_status, company_id)
VALUES ('Junior Junior Developer', 'Junior Developer', 1000, 'EUR', 'FULL', 'OPEN', 1);
INSERT INTO public.vacancy(description, "position", salary, currency, employment, vacancy_status, company_id)
VALUES ('Middle WebUI Developer', 'Middle Developer', 3000, 'EUR', 'HOURLY', 'OPEN', 1);
INSERT INTO public.vacancy(description, "position", salary, currency, hot_vacancy, employment, vacancy_status,
                           company_id)
VALUES ('Senior Angular Developer', 'Senior Developer', 4000, 'USD', true, 'PART_TIME', 'OPEN', 1);
INSERT INTO public.vacancy(description, "position", salary, currency, employment, vacancy_status, company_id)
VALUES ('Junior Java Developer', 'Junior Developer', 1000, 'USD', 'FULL', 'OPEN', 3);
INSERT INTO public.vacancy(description, "position", salary, currency, hot_vacancy, employment, vacancy_status,
                           company_id)
VALUES ('Middle WebUI Developer', 'Middle Developer', 3000, 'UAH', true, 'HOURLY', 'OPEN', 3);
INSERT INTO public.vacancy(description, "position", salary, currency, hot_vacancy, employment, vacancy_status,
                           company_id)
VALUES ('Senior React Developer', 'Senior Developer', 4000, 'EUR', true, 'PART_TIME', 'OPEN', 3);

INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Problem-solving skills', 1);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Good knowledge of oxmxyadlvfjbdjQL, JUnit, bash, ant, Linux', 1);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Strong organizational and leadership skills', 2);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Proven experience as a Java Software Engineer, Java Developer or similar role', 2);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Strong experience building Java EE applications, preferably using Java 8', 3);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Degree in Computer Science, Engineering or relevant field', 3);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Bachelor degree in computer science or related fields', 4);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Ability to program production-grade applications with Node.js', 4);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Solid understanding of Linux operating system', 5);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Good understanding of the networking', 5);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Experience with any public cloud provider', 6);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Experience in and understanding of CI', 6);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Experience with monitoring and log management ', 7);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Degree in Computer Science, Engineering or relevant field', 7);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Extensive understanding of enterprise monitoring solutions', 8);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Good written and verbal English communications skills', 8);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('Deep knowledge DBs, RDBMS, SQL', 9);
INSERT INTO public.requirement(description, vacancy_id)
VALUES ('You are adept at writing unit tests and testable code, and working under distributed version control', 9);

--
-- TOC entry 2326 (class 0 OID 0)
-- Dependencies: 197
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);

-- TOC entry 2117 (class 2606 OID 43543)
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (address_id);


--
-- TOC entry 2119 (class 2606 OID 43554)
-- Name: company company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT company_pkey PRIMARY KEY (company_id);


--
-- TOC entry 2121 (class 2606 OID 43562)
-- Name: contact contact_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contact
    ADD CONSTRAINT contact_pkey PRIMARY KEY (contact_id);


--
-- TOC entry 2123 (class 2606 OID 43570)
-- Name: resume resume_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resume
    ADD CONSTRAINT resume_pkey PRIMARY KEY (resume_id);


--
-- TOC entry 2125 (class 2606 OID 43578)
-- Name: education education_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.education
    ADD CONSTRAINT education_pkey PRIMARY KEY (education_id);


--
-- TOC entry 2127 (class 2606 OID 43586)
-- Name: job job_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job
    ADD CONSTRAINT job_pkey PRIMARY KEY (job_id);


--
-- TOC entry 2129 (class 2606 OID 43594)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2131 (class 2606 OID 43602)
-- Name: requirement requirement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requirement
    ADD CONSTRAINT requirement_pkey PRIMARY KEY (requirement_id);


--
-- TOC entry 2133 (class 2606 OID 43610)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


--
-- TOC entry 2135 (class 2606 OID 43618)
-- Name: skill skill_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
    ADD CONSTRAINT skill_pkey PRIMARY KEY (skill_id);

--
-- TOC entry 2139 (class 2606 OID 43632)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2141 (class 2606 OID 43640)
-- Name: vacancy vacancy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy
    ADD CONSTRAINT vacancy_pkey PRIMARY KEY (vacancy_id);


--
-- TOC entry 2143 (class 2606 OID 43645)
-- Name: verificationtoken verificationtoken_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verificationtoken
    ADD CONSTRAINT verificationtoken_pkey PRIMARY KEY (id);

--
-- TOC entry 2147 (class 2606 OID 43663)
-- Name: resume fk237ed6jpj1pxhm0qst2qb5h4h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resume
    ADD CONSTRAINT fk237ed6jpj1pxhm0qst2qb5h4h FOREIGN KEY (education_id) REFERENCES public.education (education_id);


--
-- TOC entry 2145 (class 2606 OID 43653)
-- Name: company fk4gffpyf7lepjqbbo9a2iwmqda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT fk4gffpyf7lepjqbbo9a2iwmqda FOREIGN KEY (contact_id) REFERENCES public.contact (contact_id);


--
-- TOC entry 2155 (class 2606 OID 43703)
-- Name: vacancy fk9p6ux32w6e3jewgvnvcjuqdgg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy
    ADD CONSTRAINT fk9p6ux32w6e3jewgvnvcjuqdgg FOREIGN KEY (company_id) REFERENCES public.company (company_id);

--
-- TOC entry 2156 (class 2606 OID 43708)
-- Name: verificationtoken fk_verify_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verificationtoken
    ADD CONSTRAINT fk_verify_user FOREIGN KEY (user_id) REFERENCES public.users (user_id);

--
-- TOC entry 2144 (class 2606 OID 43648)
-- Name: company fkgfifm4874ce6mecwj54wdb3ma; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT fkgfifm4874ce6mecwj54wdb3ma FOREIGN KEY (address_id) REFERENCES public.address (address_id);


--
-- TOC entry 2150 (class 2606 OID 43678)
-- Name: person fkhycdpp7nxyg8gctdb24udn08j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT fkhycdpp7nxyg8gctdb24udn08j FOREIGN KEY (contact_id) REFERENCES public.contact (contact_id);


--
-- TOC entry 2154 (class 2606 OID 43698)
-- Name: user_role fkj345gk1bovqvfame88rcx7yyx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES public.users (user_id);


--
-- TOC entry 2149 (class 2606 OID 43673)
-- Name: person fkk7rgn6djxsv2j2bv1mvuxd4m9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT fkk7rgn6djxsv2j2bv1mvuxd4m9 FOREIGN KEY (address_id) REFERENCES public.address (address_id);


--
-- TOC entry 2152 (class 2606 OID 43688)
-- Name: skill fkli4yjkukgrskb6f8uywgkgjdq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
    ADD CONSTRAINT fkli4yjkukgrskb6f8uywgkgjdq FOREIGN KEY (resume_id) REFERENCES public.resume (resume_id);


--
-- TOC entry 2151 (class 2606 OID 43683)
-- Name: requirement fkmffp440qbtsg6v32o61v0c3yk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requirement
    ADD CONSTRAINT fkmffp440qbtsg6v32o61v0c3yk FOREIGN KEY (vacancy_id) REFERENCES public.vacancy (vacancy_id);


--
-- TOC entry 2148 (class 2606 OID 43668)
-- Name: job fkqb7vf93h6yrgmc1yn2igj5msl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job
    ADD CONSTRAINT fkqb7vf93h6yrgmc1yn2igj5msl FOREIGN KEY (resume_id) REFERENCES public.resume (resume_id);


--
-- TOC entry 2146 (class 2606 OID 43658)
-- Name: company fksxe9t9istcdt2mtdbvgh83a9g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT fksxe9t9istcdt2mtdbvgh83a9g FOREIGN KEY (user_id) REFERENCES public.users (user_id);


--
-- TOC entry 2153 (class 2606 OID 43693)
-- Name: user_role fkt7e7djp752sqn6w22i6ocqy6q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkt7e7djp752sqn6w22i6ocqy6q FOREIGN KEY (role_id) REFERENCES public.roles (role_id);



-- Completed on 2019-04-24 13:17:40

--
-- oxmxyadlvfjbdjQL database dump complete
--

