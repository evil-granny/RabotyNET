package ua.softserve.ita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Letter;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.model.User;

@Service("generateService")
public class GenerateLetter{

    @Autowired
    LetterService letterService;

    public void sendValidationEmail(User user, String linkOfValidation){
        Letter letter = new Letter();

        letter.seteMail(user.getLogin());
        letter.setSubject("Ragistration on website RabotyNet");
        String validationLink=linkOfValidation;
        String content = "Your mail has been specified for registration on the site of RabotyNET " +
                "to complete the registration by clicking on the link:" + validationLink +
                "If you do not know about this, ignore this message;";
        letter.setContent(content);
        letter.setWithAttachment(false);

        letterService.sendLetter(letter);

    }

    public void sendPersonEmail(Person person){
        Letter letter = new Letter();

        letter.seteMail(person.getContacts().getEmail());
        letter.setSubject("Hello on ");
        String someLink="someLink";
        String content = "some text" + someLink;
        letter.setContent(content);
        letter.setWithAttachment(false);

        letterService.sendLetter(letter);

    }

    public void sendPersonWithAttachment(Person person, String linkToAttachment){
        Letter letter = new Letter();

        letter.seteMail(person.getContacts().getEmail());
        letter.setSubject("Hello on ");
        String validationLink="someLink";
        String content = "some text";
        letter.setContent(content);
        letter.setWithAttachment(true);

        letter.setLinkForAttachment(linkToAttachment);

        letterService.sendLetter(letter);

    }

    public void sendCompanyApprove(Company company, String linkToAttachment){

    }


}
