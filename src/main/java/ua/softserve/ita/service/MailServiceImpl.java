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
import ua.softserve.ita.model.Person;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
    JavaMailSender mailSender;

	@Override
	public void sendEmail(Object object) {

		//Person person = (Person) object;
		Letter letter = (Letter) object;
		MimeMessagePreparator preparator;

		if (letter.isWithAttachment()) {preparator = getContentWithAttachement(letter);}
		//MimeMessagePreparator preparator = getContentWtihAttachementMessagePreparator(person);
		else {preparator = getContent(letter);}


		try {
			mailSender.send(preparator);
			System.out.println("Message With Attachement has been sent.............................");
			//preparator = getContentAsInlineResourceMessagePreparator(person);
			//mailSender.send(preparator);
			//System.out.println("Message With Inline Resource has been sent.........................");



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

				// Add an inline resource.
				// use the true flag to indicate you need a multipart message
				helper.setText("<html><body><p>" + content + "</p>></body></html>", true);
				//helper.addInline("company-logo", new ClassPathResource("linux-icon.png"));
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

				// Add a resource as an attachment
				FileSystemResource file = new FileSystemResource(new File(letter.getLinkForAttachment()));
				helper.addAttachment("attacment",file);

			}
		};
		return preparator;
	}



}
