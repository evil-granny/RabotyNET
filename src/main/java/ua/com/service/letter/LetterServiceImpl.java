package ua.com.service.letter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.model.Letter;
import ua.com.service.mail.MailService;

@Service
public class LetterServiceImpl implements LetterService {

    private final MailService mailService;

    @Autowired
    public LetterServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void sendLetter(Letter letter) {
        mailService.sendEmail(letter);
    }

}
