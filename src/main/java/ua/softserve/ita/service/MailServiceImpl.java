package ua.softserve.ita.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.Letter;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
    JavaMailSender mailSender;

	@Override
	public void sendEmail(Object object) {

		Letter letter = (Letter) object;
		MimeMessagePreparator preparator;

		if (letter.isWithAttachment()) {preparator = getContentWithAttachement(letter);}
		else {preparator = getContent(letter);}
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private MimeMessagePreparator getContent(final Letter letter) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				helper.setSubject(letter.getSubject());
				helper.setFrom("rabotynetch082@gmail.com");
				helper.setTo(letter.geteMail());
				String content = letter.getContent();

				helper.setText("<html><body><p>" + content + "</p><img src='cid:company-logo'></body></html>", true);
				helper.addInline("company-logo", new ClassPathResource("linux-icon.png"));

			}
		};
		return preparator;
	}

	private MimeMessagePreparator getContentWithAttachement(final Letter letter) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

				helper.setSubject(letter.getSubject());
				helper.setFrom("rabotynetch082@gmail.com");
				helper.setTo(letter.geteMail());
				helper.setText(letter.getContent());

				FileSystemResource file = new FileSystemResource(new File(letter.getLinkForAttachment()));
				helper.addAttachment("attacment",file);

			}
		};
		return preparator;
	}



}
