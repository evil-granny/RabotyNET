BEGIN;
INSERT INTO public.users(enabled, login, password) VALUES (true, 'admin@gmail.com', '$2a$10$E2.PwtnpF2p6aB3NFM3Qo.TarTYsaiWD0yTZ7qY1U3K.ybKxNvCku');
INSERT INTO public.users(enabled, login, password) VALUES (true, 'cowner@gmail.com', '$2a$10$DmeWO6UlY/m2QjJaxLGUzezqOotvJmpzbBmZGBr8o/HHeNUuCWcpK');
INSERT INTO public.users(enabled, login, password) VALUES (true, 'user@gmail.com', '$2a$10$t31PsVNWl8eaWr9/gPwKKeX.4Q2grl12wmiRrN9fEZDMlMGHwA92m');

INSERT INTO public.roles(role_id, type)  VALUES (1, 'admin');
INSERT INTO public.roles(role_id, type)  VALUES (2, 'cowner');
INSERT INTO public.roles(role_id, type)  VALUES (3, 'user');

INSERT INTO public.user_role(user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role(user_id, role_id) VALUES (2, 2);
INSERT INTO public.user_role(user_id, role_id) VALUES (2, 3);
INSERT INTO public.user_role(user_id, role_id) VALUES (3, 3);

INSERT INTO public.address(
   building, city, country, street, zip_code)
  VALUES ('20', 'Chernivtsi', 'Ukraine', 'Holovna', 58000);
INSERT INTO public.address(
   building, city, country, street, zip_code)
  VALUES ('33', 'Chernivtsi', 'Ukraine', 'Shevchenka', 58000);
INSERT INTO public.address(
   building, city, country, street, zip_code)
  VALUES ('246', 'Chernivtsi', 'Ukraine', 'Holovna', 58000);
INSERT INTO public.address(
   building, city, country, street, zip_code)
  VALUES ('33', 'Chernivtsi', 'Ukraine', 'Komarova', 58000);
INSERT INTO public.address(
   building, city, country, street, zip_code)
  VALUES ('15', 'Chernivtsi', 'Ukraine', 'Rivnenska', 58000);

INSERT INTO public.education(
   degree, graduation, school, specialty)
  VALUES ('Master', 2016, 'ChNU', 'Software Engineer');
INSERT INTO public.education(
   degree, graduation, school, specialty)
  VALUES ('Master', 2009, 'KPI', 'Software Engineer');

INSERT INTO public.contact(
   email, phone_number)
  VALUES ('den.ohorodnik@gmail.com', '+380973999060');
INSERT INTO public.contact(
   email, phone_number)
  VALUES ('jon.snow@gmail.com', '+380973219033');
INSERT INTO public.contact(
   email, phone_number)
  VALUES ('softserve.inc@gmail.com', '+380322409999');
INSERT INTO public.contact(
   email, phone_number)
  VALUES ('inventorsoft.inc@gmail.com', '+380984529012');
INSERT INTO public.contact(
   email, phone_number)
  VALUES ('valsoft@gmail.com', '+380972993270');

INSERT INTO public.person(user_id)
  VALUES (1);
INSERT INTO public.person(
   user_id, birthday, first_name, last_name, address_id, contact_id, photo_id)
  VALUES (2,' 1986-11-26', 'Jon', 'Snow', 2, 2, null);
INSERT INTO public.person(
   user_id, birthday, first_name, last_name, address_id, contact_id, photo_id)
  VALUES (3,' 1999-06-04', 'Denys', 'Ohorodnik', 1, 1, null);

INSERT INTO public.resume(
   "position", education_id, user_id)
  VALUES ('Junior Developer', 1, 2);
INSERT INTO public.resume(
   "position", education_id, user_id)
  VALUES ('Middle Developer', 2, 3);

INSERT INTO public.skill(
   description, title, print_pdf, resume_id)
  VALUES ('Spring MVC', 'MVC', true, 1);
INSERT INTO public.skill(
   description, title, print_pdf, resume_id)
  VALUES ('Spring Boot', 'Spring', true, 1);
INSERT INTO public.skill(
   description, title, print_pdf, resume_id)
  VALUES ('Good skill', 'Linux', true, 2);
INSERT INTO public.skill(
   description, title, print_pdf, resume_id)
  VALUES ('Some experience', 'Angular', true, 2);
INSERT INTO public.skill(
   description, title, print_pdf, resume_id)
  VALUES ('Some experience', 'Html', true, 2);

INSERT INTO public.job(
   begin, company_name, description, "end",print_pdf, "position", resume_id)
  VALUES ('2000-10-10', 'SoftServe' , 'Junior Java Developer', '2009-03-04', true, 'Junior', 1);
INSERT INTO public.job(
   begin, company_name, description, "end",print_pdf, "position", resume_id)
  VALUES ('2006-04-08', 'ValSoft' , 'Middle Java Developer', '2009-11-04', true, 'Middle', 1);
INSERT INTO public.job(
   begin, company_name, description, "end",print_pdf, "position", resume_id)
  VALUES ('2008-03-08', 'ValSoft' , 'Middle Java developer', '2005-07-04', true, 'Middle', 2);
INSERT INTO public.job(
   begin, company_name, description, "end",print_pdf, "position", resume_id)
  VALUES ('2006-10-08', 'SoftServe' , 'Junior Java Developer', '2009-10-04', true, 'Junior', 2);
INSERT INTO public.job(
   begin, company_name, description, "end",print_pdf, "position", resume_id)
  VALUES ('2010-04-08', 'InventorSoft' , 'Senior Java Developer', '2014-11-04', true, 'Senior', 2);

INSERT INTO public.company(
   description, edrpou, name, status, website, address_id, contact_id, photo_id, user_id)
  VALUES ('We are digital advisors and providers who operate at the cutting edge of technology. We deliver the innovation, quality, and speed that our clients’ users expect.', 23456742, 'SoftServe', 'APPROVED', 'www.softserveinc.com', 3, 3, null, 2);
INSERT INTO public.company(
   description, edrpou, name, status, website, address_id, contact_id, photo_id, user_id)
  VALUES ('Second company', 55368632, 'InventorSoft', 'CREATED', 'www.inventorsoft.com', 4, 4, null, 2);
INSERT INTO public.company(
   description, edrpou, name, status, website, address_id, contact_id, photo_id, user_id)
  VALUES ('Third company', 63964221, 'ValSoft', 'BLOCKED', 'www.valsoft.com', 5, 5, null, 2);

INSERT INTO public.vacancy(
   description, "position", salary, currency, employment, vacancy_status, company_id)
  VALUES ('Junior Java Developer', 'Junior Developer', 1000, 'USD', 'FULL', 'OCCUPIED', 1);
INSERT INTO public.vacancy(
   description, "position", salary, currency, employment, vacancy_status, company_id)
  VALUES ('Middle React Developer', 'Middle Developer', 3000, 'UAH', 'HOURLY', 'OUTDATED', 1);
INSERT INTO public.vacancy(
   description, "position", salary, currency, employment, vacancy_status, company_id)
  VALUES ('Senior Angular Developer', 'Senior Developer', 4000, 'UAH', 'PART_TIME', 'OUTDATED', 1);
INSERT INTO public.vacancy(
   description, "position", salary, currency, employment, vacancy_status, company_id)
  VALUES ('Junior Junior Developer', 'Junior Developer', 1000, 'EUR', 'FULL', 'OPEN', 1);
INSERT INTO public.vacancy(
   description, "position", salary, currency, employment, vacancy_status, company_id)
  VALUES ('Middle WebUI Developer', 'Middle Developer', 3000, 'EUR', 'HOURLY', 'OPEN', 1);
INSERT INTO public.vacancy(
   description, "position", salary, currency, hot_vacancy ,employment, vacancy_status, company_id)
  VALUES ('Senior Angular Developer', 'Senior Developer', 4000, 'USD', true, 'PART_TIME', 'OPEN', 1);
INSERT INTO public.vacancy(
   description, "position", salary, currency, employment, vacancy_status, company_id)
  VALUES ('Junior Java Developer', 'Junior Developer', 1000, 'USD', 'FULL', 'OPEN', 3);
INSERT INTO public.vacancy(
   description, "position", salary, currency, hot_vacancy, employment, vacancy_status, company_id)
  VALUES ('Middle WebUI Developer', 'Middle Developer', 3000, 'UAH',true, 'HOURLY', 'OPEN', 3);
INSERT INTO public.vacancy(
   description, "position", salary, currency, hot_vacancy ,employment, vacancy_status, company_id)
  VALUES ('Senior React Developer', 'Senior Developer', 4000, 'EUR', true, 'PART_TIME', 'OPEN', 3);

INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Problem-solving skills', 1);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Good knowledge of PostgreSQL, JUnit, bash, ant, Linux', 1);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Strong organizational and leadership skills', 2);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Proven experience as a Java Software Engineer, Java Developer or similar role', 2);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Strong experience building Java EE applications, preferably using Java 8', 3);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Degree in Computer Science, Engineering or relevant field', 3);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Bachelor degree in computer science or related fields', 4);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Ability to program production-grade applications with Node.js', 4);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Solid understanding of Linux operating system', 5);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Good understanding of the networking', 5);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Experience with any public cloud provider', 6);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Experience in and understanding of CI', 6);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Experience with monitoring and log management ', 7);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Degree in Computer Science, Engineering or relevant field', 7);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Extensive understanding of enterprise monitoring solutions', 8);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Good written and verbal English communications skills', 8);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('Deep knowledge DBs, RDBMS, SQL', 9);
INSERT INTO public.requirement(
   description, vacancy_id)
  VALUES ('You are adept at writing unit tests and testable code, and working under distributed version control', 9);
COMMIT;