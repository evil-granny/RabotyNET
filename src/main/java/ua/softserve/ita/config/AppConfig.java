package ua.softserve.ita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.softserve.ita.service.CronJob;
import ua.softserve.ita.service.MyTask;

import static org.hibernate.cfg.AvailableSettings.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Timer;

@Configuration

@PropertySource("classpath:database.properties")
@PropertySource("classpath:mail.properties")
@EnableTransactionManagement(proxyTargetClass = true)

@ComponentScan(basePackages = "ua.softserve.ita")
public class AppConfig {

    @Autowired
    private Environment environment;



    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        Properties properties = new Properties();

        properties.put(DRIVER, environment.getProperty("postgresql.driver"));
        properties.put(URL, environment.getProperty("postgresql.url"));
        properties.put(USER, environment.getProperty("postgresql.user"));
        properties.put(PASS, environment.getProperty("postgresql.password"));

        properties.put(SHOW_SQL, environment.getProperty("hibernate.show_sql"));
        properties.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put(DIALECT, environment.getProperty("hibernate.dialect"));

        properties.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        properties.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        properties.put(C3P0_ACQUIRE_INCREMENT, environment.getProperty("hibernate.c3p0.acquire_increment"));
        properties.put(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        properties.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements"));

        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.setPackagesToScan("ua.softserve.ita.model");

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());

        return transactionManager;
    }


    @Bean
    public JavaMailSender getMailSender(){


        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(environment.getProperty("mail.host"));

        mailSender.setPort(Integer.parseInt(environment.getProperty("mail.port")));
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));

        Properties javaMailProperties = new Properties();

        javaMailProperties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.transport.protocol", environment.getProperty("mail.transport.protocol"));
        javaMailProperties.put("mail.debug", environment.getProperty("mail.debug"));

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    @Bean
    public Path cronStart(){

        String dirPath ="pdf/tempPDFdir";

        Path dirPathObj = Paths.get(dirPath);

        try {
            System.out.println(dirPathObj.toRealPath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean dirExists = Files.exists(dirPathObj);
        if(dirExists) {
            try {
                System.out.println("! Directory Already Exists !" + dirPathObj.toRealPath() );
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                // Creating The New Directory Structure
                Files.createDirectories(dirPathObj);
                System.out.println("! New Directory Successfully Created !");
            } catch (IOException ioExceptionObj) {
                System.out.println("Problem Occured While Creating The Directory Structure= " + ioExceptionObj.getMessage());
            }
        }

        Timer t = new Timer();
        MyTask mTask = new MyTask(dirPathObj);
        // This task is scheduled to run every 10 seconds
        t.scheduleAtFixedRate(mTask, 0, 300000);


        return dirPathObj;
    }



}
