package ua.com.service.mail;

import ua.com.model.Letter;

public interface MailService {

    void sendEmail(final Letter letter);

}
