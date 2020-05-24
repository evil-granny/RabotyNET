# RabotyNET
## :eight_spoked_asterisk: Appointment 
Web application for searching job. It is a server part.

## :eight_spoked_asterisk: Description
## Project allows user: 
* create, update and close vacancies
* create, update resume
* make pdf fom resume and sent it on the mail
* send resume on the vacancy
* create, update, approve, delete company
* reject company
* search vacancy and resume
* has several roles
* log in, log out and sing in

## :eight_spoked_asterisk: Installation
* clone project from repository
* run sql script for restoring database in pgAdmin

`RabotyNETlocalhost.sql` for localhost
* Run `mvn clean install` in console
* Change:
```
postgresql.url=jdbc:postgresql://localhost:5432/nameofdatabase       
postgresql.user=user'sname
postgresql.password=yourpassword
```
* Configure server

![Configuration for server](https://github.com/evil-granny/RabotyNET/blob/development/screenshots/server.png) 

![Configuration for deployment](https://github.com/evil-granny/RabotyNET/blob/development/screenshots/deployment.png) 
## :eight_spoked_asterisk: Tools and libraries
* Spring 5
* Javax.mail
* Hibernate 5
* Postgresql 
* Lombok
* Spring Security
* Log4j
* Junit
* Maven
* Java 8

## :eight_spoked_asterisk: Author

Project was created by Denys Ohorodnik [(t.me)](https://t.me/denys_ohorodnik) [(mail)](https://den.ohorodnik@gmail.com).

