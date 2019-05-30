package ua.softserve.ita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.softserve.ita.service.PdfResumeService;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration

@PropertySource("classpath:database.properties")
@PropertySource("classpath:mail.properties")
@PropertySource("classpath:cronCleanTask.properties")
@EnableTransactionManagement(proxyTargetClass = true)

//@EnableAsync
@EnableScheduling

@ComponentScan(basePackages = "ua.softserve.ita")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private PdfResumeService pdfResumeService;

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
    public JavaMailSender getMailSender() {

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
    public Path createDirectoryForCvPdf() {

        final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());

        final String dirPath = "pdf/tempPDFdir";

        Path dirPathObj = Paths.get(dirPath);

        boolean dirExists = Files.exists(dirPathObj);

        if (!dirExists) {

            try {
                Files.createDirectories(dirPathObj);

            } catch (IOException e) {

                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

        return dirPathObj;
    }

    @Scheduled(cron = "${cron.cleanTempFileCvPdf}")
    public void cleanTempFile() {

        final String SAVE_DIRECTORY_FOR_PDF_DOC = "pdf/tempPDFdir";

        final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());

        final String PREFIX_FILE_NAME = "pdfCV";

        Path path = Paths.get(SAVE_DIRECTORY_FOR_PDF_DOC);

        boolean toClean = false;

        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(path, "pdfCV" + "*")) {

            for (final Path newDirectoryStreamItem : newDirectoryStream) {

                //String pdfName = newDirectoryStreamItem.getFileName().toString();

                //CV cvForClean = cvService.findByPdfName(pdfName).orElseThrow(() -> new ResourceNotFoundException(String.format("CV with id: %d not found")));

                //cvForClean.setPdfResume(null);

                //CV newCleanCV = cvService.update(cvForClean);

                //System.out.println("clean cv");

                Files.delete(newDirectoryStreamItem);

                System.out.println("hello my cron");

                toClean = true;

            }

            if (toClean) pdfResumeService.deleteAll();

            System.out.println("clean table pdf");

        } catch (final Exception e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }
    }


}
