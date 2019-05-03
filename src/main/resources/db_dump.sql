--
-- PostgreSQL database dump
--

-- Dumped from database version 10.7
-- Dumped by pg_dump version 11.1

-- Started on 2019-04-29 15:40:57

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
-- TOC entry 197 (class 1259 OID 17171)
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.address (
    address_id bigint NOT NULL,
    apartment character varying(5),
    building character varying(5),
    city character varying(15) NOT NULL,
    country character varying(20) NOT NULL,
    street character varying(30),
    zip_code integer
);


ALTER TABLE public.address OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 17169)
-- Name: address_address_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.address_address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.address_address_id_seq OWNER TO postgres;

--
-- TOC entry 2305 (class 0 OID 0)
-- Dependencies: 196
-- Name: address_address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.address_address_id_seq OWNED BY public.address.address_id;


--
-- TOC entry 199 (class 1259 OID 17179)
-- Name: company; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.company (
    company_id bigint NOT NULL,
    approved boolean,
    description character varying(255),
    edrpou character varying(30) NOT NULL,
    logo character varying(255),
    name character varying(30) NOT NULL,
    website character varying(50),
    address_id bigint NOT NULL,
    contacts_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.company OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 17177)
-- Name: company_company_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.company_company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.company_company_id_seq OWNER TO postgres;

--
-- TOC entry 2306 (class 0 OID 0)
-- Dependencies: 198
-- Name: company_company_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.company_company_id_seq OWNED BY public.company.company_id;


--
-- TOC entry 201 (class 1259 OID 17190)
-- Name: contacts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contacts (
    contacts_id bigint NOT NULL,
    email character varying(50),
    phone_number character varying(255)
);


ALTER TABLE public.contacts OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 17188)
-- Name: contacts_contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contacts_contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contacts_contacts_id_seq OWNER TO postgres;

--
-- TOC entry 2307 (class 0 OID 0)
-- Dependencies: 200
-- Name: contacts_contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contacts_contacts_id_seq OWNED BY public.contacts.contacts_id;


--
-- TOC entry 203 (class 1259 OID 17198)
-- Name: cv; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cv (
    cv_id bigint NOT NULL,
    photo character varying(255),
    "position" character varying(50) NOT NULL,
    education_id bigint NOT NULL
);


ALTER TABLE public.cv OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 17196)
-- Name: cv_cv_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cv_cv_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cv_cv_id_seq OWNER TO postgres;

--
-- TOC entry 2308 (class 0 OID 0)
-- Dependencies: 202
-- Name: cv_cv_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cv_cv_id_seq OWNED BY public.cv.cv_id;


--
-- TOC entry 205 (class 1259 OID 17206)
-- Name: education; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.education (
    education_id bigint NOT NULL,
    degree character varying(30) NOT NULL,
    graduation integer,
    school character varying(50) NOT NULL,
    specialty character varying(100)
);


ALTER TABLE public.education OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 17204)
-- Name: education_education_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.education_education_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.education_education_id_seq OWNER TO postgres;

--
-- TOC entry 2309 (class 0 OID 0)
-- Dependencies: 204
-- Name: education_education_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.education_education_id_seq OWNED BY public.education.education_id;


--
-- TOC entry 221 (class 1259 OID 17281)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 17214)
-- Name: job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.job (
    job_id bigint NOT NULL,
    begin date,
    companyname character varying(50),
    description character varying(200),
    "end" date,
    "position" character varying(40) NOT NULL,
    cv_id bigint NOT NULL
);


ALTER TABLE public.job OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 17212)
-- Name: job_job_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.job_job_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.job_job_id_seq OWNER TO postgres;

--
-- TOC entry 2310 (class 0 OID 0)
-- Dependencies: 206
-- Name: job_job_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.job_job_id_seq OWNED BY public.job.job_id;


--
-- TOC entry 208 (class 1259 OID 17220)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    user_id bigint NOT NULL,
    birthday date NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    photo bytea,
    address_id bigint NOT NULL,
    contacts_id bigint NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 17230)
-- Name: requirement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.requirement (
    requirement_id bigint NOT NULL,
    description character varying(100) NOT NULL,
    vacancy_id bigint NOT NULL
);


ALTER TABLE public.requirement OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 17228)
-- Name: requirement_requirement_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.requirement_requirement_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.requirement_requirement_id_seq OWNER TO postgres;

--
-- TOC entry 2311 (class 0 OID 0)
-- Dependencies: 209
-- Name: requirement_requirement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.requirement_requirement_id_seq OWNED BY public.requirement.requirement_id;


--
-- TOC entry 212 (class 1259 OID 17238)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    role_id bigint NOT NULL,
    type character varying(10) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 17236)
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_role_id_seq OWNER TO postgres;

--
-- TOC entry 2312 (class 0 OID 0)
-- Dependencies: 211
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_role_id_seq OWNED BY public.roles.role_id;


--
-- TOC entry 214 (class 1259 OID 17246)
-- Name: skill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill (
    skill_id bigint NOT NULL,
    description character varying(255),
    title character varying(30) NOT NULL,
    cv_id bigint NOT NULL
);


ALTER TABLE public.skill OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 17244)
-- Name: skill_skill_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.skill_skill_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.skill_skill_id_seq OWNER TO postgres;

--
-- TOC entry 2313 (class 0 OID 0)
-- Dependencies: 213
-- Name: skill_skill_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.skill_skill_id_seq OWNED BY public.skill.skill_id;


--
-- TOC entry 215 (class 1259 OID 17252)
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17257)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    enabled boolean,
    login character varying(50) NOT NULL,
    matchingpassword character varying(255),
    password character varying(200) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17255)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 2314 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 219 (class 1259 OID 17268)
-- Name: vacancy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vacancy (
    vacancy_id bigint NOT NULL,
    employment character varying(255),
    "position" character varying(40) NOT NULL,
    salary integer,
    company_id bigint NOT NULL
);


ALTER TABLE public.vacancy OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17266)
-- Name: vacancy_vacancy_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vacancy_vacancy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vacancy_vacancy_id_seq OWNER TO postgres;

--
-- TOC entry 2315 (class 0 OID 0)
-- Dependencies: 218
-- Name: vacancy_vacancy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vacancy_vacancy_id_seq OWNED BY public.vacancy.vacancy_id;


--
-- TOC entry 220 (class 1259 OID 17274)
-- Name: verificationtoken; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.verificationtoken (
    id bigint NOT NULL,
    expirydate timestamp without time zone,
    token character varying(255),
    user_id bigint NOT NULL
);


ALTER TABLE public.verificationtoken OWNER TO postgres;

--
-- TOC entry 2101 (class 2604 OID 17174)
-- Name: address address_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address ALTER COLUMN address_id SET DEFAULT nextval('public.address_address_id_seq'::regclass);


--
-- TOC entry 2102 (class 2604 OID 17182)
-- Name: company company_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company ALTER COLUMN company_id SET DEFAULT nextval('public.company_company_id_seq'::regclass);


--
-- TOC entry 2103 (class 2604 OID 17193)
-- Name: contacts contacts_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacts ALTER COLUMN contacts_id SET DEFAULT nextval('public.contacts_contacts_id_seq'::regclass);


--
-- TOC entry 2104 (class 2604 OID 17201)
-- Name: cv cv_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cv ALTER COLUMN cv_id SET DEFAULT nextval('public.cv_cv_id_seq'::regclass);


--
-- TOC entry 2105 (class 2604 OID 17209)
-- Name: education education_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.education ALTER COLUMN education_id SET DEFAULT nextval('public.education_education_id_seq'::regclass);


--
-- TOC entry 2106 (class 2604 OID 17217)
-- Name: job job_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job ALTER COLUMN job_id SET DEFAULT nextval('public.job_job_id_seq'::regclass);


--
-- TOC entry 2107 (class 2604 OID 17233)
-- Name: requirement requirement_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requirement ALTER COLUMN requirement_id SET DEFAULT nextval('public.requirement_requirement_id_seq'::regclass);


--
-- TOC entry 2108 (class 2604 OID 17241)
-- Name: roles role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN role_id SET DEFAULT nextval('public.roles_role_id_seq'::regclass);


--
-- TOC entry 2109 (class 2604 OID 17249)
-- Name: skill skill_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill ALTER COLUMN skill_id SET DEFAULT nextval('public.skill_skill_id_seq'::regclass);


--
-- TOC entry 2110 (class 2604 OID 17260)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 2111 (class 2604 OID 17271)
-- Name: vacancy vacancy_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy ALTER COLUMN vacancy_id SET DEFAULT nextval('public.vacancy_vacancy_id_seq'::regclass);


--
-- TOC entry 2275 (class 0 OID 17171)
-- Dependencies: 197
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.address (address_id, apartment, building, city, country, street, zip_code) VALUES (1, NULL, NULL, 'NY', 'USA', NULL, NULL);
INSERT INTO public.address (address_id, apartment, building, city, country, street, zip_code) VALUES (2, NULL, NULL, 'Chicago', 'USA', NULL, NULL);
INSERT INTO public.address (address_id, apartment, building, city, country, street, zip_code) VALUES (3, NULL, NULL, 'Springfield', 'USA', NULL, NULL);
INSERT INTO public.address (address_id, apartment, building, city, country, street, zip_code) VALUES (4, NULL, NULL, 'Springfield', 'USA', NULL, NULL);
INSERT INTO public.address (address_id, apartment, building, city, country, street, zip_code) VALUES (5, NULL, NULL, 'Toronto', 'Canada', NULL, NULL);


--
-- TOC entry 2277 (class 0 OID 17179)
-- Dependencies: 199
-- Data for Name: company; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2279 (class 0 OID 17190)
-- Dependencies: 201
-- Data for Name: contacts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.contacts (contacts_id, email, phone_number) VALUES (1, 'John@gmail.com', '0502323265');
INSERT INTO public.contacts (contacts_id, email, phone_number) VALUES (2, 'tribiani@gmail.com', '0672654578');
INSERT INTO public.contacts (contacts_id, email, phone_number) VALUES (3, 'homer@gmail.com', '0954587845');
INSERT INTO public.contacts (contacts_id, email, phone_number) VALUES (4, 'bart@gmail.com', '0972331212');
INSERT INTO public.contacts (contacts_id, email, phone_number) VALUES (5, 'lisa@gmail.com', '0678897787');


--
-- TOC entry 2281 (class 0 OID 17198)
-- Dependencies: 203
-- Data for Name: cv; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2283 (class 0 OID 17206)
-- Dependencies: 205
-- Data for Name: education; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2285 (class 0 OID 17214)
-- Dependencies: 207
-- Data for Name: job; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2286 (class 0 OID 17220)
-- Dependencies: 208
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person (user_id, birthday, first_name, last_name, photo, address_id, contacts_id) VALUES (1, '1977-12-25', 'John', 'Golt', NULL, 1, 1);
INSERT INTO public.person (user_id, birthday, first_name, last_name, photo, address_id, contacts_id) VALUES (2, '1975-02-12', 'Joe', 'Tribiany', NULL, 2, 2);
INSERT INTO public.person (user_id, birthday, first_name, last_name, photo, address_id, contacts_id) VALUES (3, '1953-10-18', 'Homer', 'Simpson', NULL, 3, 3);
INSERT INTO public.person (user_id, birthday, first_name, last_name, photo, address_id, contacts_id) VALUES (4, '1982-05-23', 'Bart', 'Simpson', NULL, 4, 4);
INSERT INTO public.person (user_id, birthday, first_name, last_name, photo, address_id, contacts_id) VALUES (5, '1990-02-03', 'Lisa', 'Simpson', NULL, 5, 5);


--
-- TOC entry 2288 (class 0 OID 17230)
-- Dependencies: 210
-- Data for Name: requirement; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2290 (class 0 OID 17238)
-- Dependencies: 212
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2292 (class 0 OID 17246)
-- Dependencies: 214
-- Data for Name: skill; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2293 (class 0 OID 17252)
-- Dependencies: 215
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2295 (class 0 OID 17257)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2297 (class 0 OID 17268)
-- Dependencies: 219
-- Data for Name: vacancy; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2298 (class 0 OID 17274)
-- Dependencies: 220
-- Data for Name: verificationtoken; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2316 (class 0 OID 0)
-- Dependencies: 196
-- Name: address_address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.address_address_id_seq', 1, false);


--
-- TOC entry 2317 (class 0 OID 0)
-- Dependencies: 198
-- Name: company_company_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.company_company_id_seq', 1, false);


--
-- TOC entry 2318 (class 0 OID 0)
-- Dependencies: 200
-- Name: contacts_contacts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contacts_contacts_id_seq', 1, false);


--
-- TOC entry 2319 (class 0 OID 0)
-- Dependencies: 202
-- Name: cv_cv_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cv_cv_id_seq', 1, false);


--
-- TOC entry 2320 (class 0 OID 0)
-- Dependencies: 204
-- Name: education_education_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.education_education_id_seq', 1, false);


--
-- TOC entry 2321 (class 0 OID 0)
-- Dependencies: 221
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- TOC entry 2322 (class 0 OID 0)
-- Dependencies: 206
-- Name: job_job_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.job_job_id_seq', 1, false);


--
-- TOC entry 2323 (class 0 OID 0)
-- Dependencies: 209
-- Name: requirement_requirement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.requirement_requirement_id_seq', 1, false);


--
-- TOC entry 2324 (class 0 OID 0)
-- Dependencies: 211
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_role_id_seq', 1, false);


--
-- TOC entry 2325 (class 0 OID 0)
-- Dependencies: 213
-- Name: skill_skill_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.skill_skill_id_seq', 1, false);


--
-- TOC entry 2326 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 1, false);


--
-- TOC entry 2327 (class 0 OID 0)
-- Dependencies: 218
-- Name: vacancy_vacancy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vacancy_vacancy_id_seq', 1, false);


--
-- TOC entry 2113 (class 2606 OID 17176)
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (address_id);


--
-- TOC entry 2115 (class 2606 OID 17187)
-- Name: company company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT company_pkey PRIMARY KEY (company_id);


--
-- TOC entry 2117 (class 2606 OID 17195)
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (contacts_id);


--
-- TOC entry 2119 (class 2606 OID 17203)
-- Name: cv cv_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cv
    ADD CONSTRAINT cv_pkey PRIMARY KEY (cv_id);


--
-- TOC entry 2121 (class 2606 OID 17211)
-- Name: education education_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.education
    ADD CONSTRAINT education_pkey PRIMARY KEY (education_id);


--
-- TOC entry 2123 (class 2606 OID 17219)
-- Name: job job_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job
    ADD CONSTRAINT job_pkey PRIMARY KEY (job_id);


--
-- TOC entry 2125 (class 2606 OID 17227)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2127 (class 2606 OID 17235)
-- Name: requirement requirement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requirement
    ADD CONSTRAINT requirement_pkey PRIMARY KEY (requirement_id);


--
-- TOC entry 2129 (class 2606 OID 17243)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


--
-- TOC entry 2131 (class 2606 OID 17251)
-- Name: skill skill_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
    ADD CONSTRAINT skill_pkey PRIMARY KEY (skill_id);


--
-- TOC entry 2133 (class 2606 OID 17280)
-- Name: user_role uk_it77eq964jhfqtu54081ebtio; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT uk_it77eq964jhfqtu54081ebtio UNIQUE (role_id);


--
-- TOC entry 2135 (class 2606 OID 17265)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2137 (class 2606 OID 17273)
-- Name: vacancy vacancy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy
    ADD CONSTRAINT vacancy_pkey PRIMARY KEY (vacancy_id);


--
-- TOC entry 2139 (class 2606 OID 17278)
-- Name: verificationtoken verificationtoken_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verificationtoken
    ADD CONSTRAINT verificationtoken_pkey PRIMARY KEY (id);


--
-- TOC entry 2143 (class 2606 OID 17298)
-- Name: cv fk237ed6jpj1pxhm0qst2qb5h4h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cv
    ADD CONSTRAINT fk237ed6jpj1pxhm0qst2qb5h4h FOREIGN KEY (education_id) REFERENCES public.education(education_id);


--
-- TOC entry 2141 (class 2606 OID 17288)
-- Name: company fk4gffpyf7lepjqbbo9a2iwmqda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT fk4gffpyf7lepjqbbo9a2iwmqda FOREIGN KEY (contacts_id) REFERENCES public.contacts(contacts_id);


--
-- TOC entry 2151 (class 2606 OID 17338)
-- Name: vacancy fk9p6ux32w6e3jewgvnvcjuqdgg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacancy
    ADD CONSTRAINT fk9p6ux32w6e3jewgvnvcjuqdgg FOREIGN KEY (company_id) REFERENCES public.company(company_id);


--
-- TOC entry 2152 (class 2606 OID 17343)
-- Name: verificationtoken fk_verify_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verificationtoken
    ADD CONSTRAINT fk_verify_user FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2140 (class 2606 OID 17283)
-- Name: company fkgfifm4874ce6mecwj54wdb3ma; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT fkgfifm4874ce6mecwj54wdb3ma FOREIGN KEY (address_id) REFERENCES public.address(address_id);


--
-- TOC entry 2146 (class 2606 OID 17313)
-- Name: person fkhycdpp7nxyg8gctdb24udn08j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT fkhycdpp7nxyg8gctdb24udn08j FOREIGN KEY (contacts_id) REFERENCES public.contacts(contacts_id);


--
-- TOC entry 2150 (class 2606 OID 17333)
-- Name: user_role fkj345gk1bovqvfame88rcx7yyx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2145 (class 2606 OID 17308)
-- Name: person fkk7rgn6djxsv2j2bv1mvuxd4m9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT fkk7rgn6djxsv2j2bv1mvuxd4m9 FOREIGN KEY (address_id) REFERENCES public.address(address_id);


--
-- TOC entry 2148 (class 2606 OID 17323)
-- Name: skill fkli4yjkukgrskb6f8uywgkgjdq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
    ADD CONSTRAINT fkli4yjkukgrskb6f8uywgkgjdq FOREIGN KEY (cv_id) REFERENCES public.cv(cv_id);


--
-- TOC entry 2147 (class 2606 OID 17318)
-- Name: requirement fkmffp440qbtsg6v32o61v0c3yk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requirement
    ADD CONSTRAINT fkmffp440qbtsg6v32o61v0c3yk FOREIGN KEY (vacancy_id) REFERENCES public.vacancy(vacancy_id);


--
-- TOC entry 2144 (class 2606 OID 17303)
-- Name: job fkqb7vf93h6yrgmc1yn2igj5msl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.job
    ADD CONSTRAINT fkqb7vf93h6yrgmc1yn2igj5msl FOREIGN KEY (cv_id) REFERENCES public.cv(cv_id);


--
-- TOC entry 2142 (class 2606 OID 17293)
-- Name: company fksxe9t9istcdt2mtdbvgh83a9g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.company
    ADD CONSTRAINT fksxe9t9istcdt2mtdbvgh83a9g FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2149 (class 2606 OID 17328)
-- Name: user_role fkt7e7djp752sqn6w22i6ocqy6q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkt7e7djp752sqn6w22i6ocqy6q FOREIGN KEY (role_id) REFERENCES public.roles(role_id);


-- Completed on 2019-04-29 15:41:02

--
-- PostgreSQL database dump complete
--

