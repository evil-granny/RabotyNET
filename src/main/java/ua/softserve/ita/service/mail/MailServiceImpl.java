package ua.softserve.ita.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.Letter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class.getName());

    private final JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(Object object) {
        Letter letter = (Letter) object;
        MimeMessagePreparator preparator;
        if (letter.isWithAttachment()) {
            preparator = getContentWithAttachment(letter);
        } else {
            preparator = getContent(letter);
        }

        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private MimeMessagePreparator getContent(final Letter letter) {
        return mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setSubject(letter.getSubject());
            helper.setFrom("rabotynetch082@gmail.com");
            helper.setTo(letter.getEMail());
            String content = letter.getContent();
            helper.setText("<html><body><p>" + content + "</p><img src='cid:company-logo'></body></html>", true);
            helper.addInline("company-logo", new ClassPathResource("logo.png"));
        };
    }

    private MimeMessagePreparator getContentWithAttachment(final Letter letter) {
        return mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(letter.getSubject());
            helper.setFrom("rabotynetch082@gmail.com");
            helper.setTo(letter.getEMail());
            helper.setText(letter.getContent());

            FileSystemResource file = new FileSystemResource(new File(letter.getLinkForAttachment()));
            String fileName = file.getFilename();

            helper.addAttachment(fileName, file);
        };
    }

}
