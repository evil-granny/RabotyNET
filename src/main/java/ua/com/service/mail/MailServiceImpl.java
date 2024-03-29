package ua.com.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ua.com.model.Letter;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class.getName());
    private static final String MAIL = "rabotynetch082@gmail.com";

    private final JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(Letter letter) {
        MimeMessagePreparator messagePreparator;
        if (letter.isWithAttachment()) {
            messagePreparator = getContentWithAttachment(letter);
        } else {
            messagePreparator = getContent(letter);
        }

        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private MimeMessagePreparator getContent(final Letter letter) {
        return mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setSubject(letter.getSubject());
            helper.setFrom(MAIL);
            helper.setTo(letter.getEmailAddress());
            String content = letter.getContent();
            helper.setText("<html><body><p>" + content + "</p><img src='cid:company-logo'></body></html>", true);
            helper.addInline("company-logo", new ClassPathResource("logo.png"));
        };
    }

    private MimeMessagePreparator getContentWithAttachment(final Letter letter) {
        return mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(letter.getSubject());
            helper.setFrom(MAIL);
            helper.setTo(letter.getEmailAddress());
            helper.setText(letter.getContent());

            FileSystemResource file = new FileSystemResource(new File(letter.getLinkForAttachment()));
            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
        };
    }

}
