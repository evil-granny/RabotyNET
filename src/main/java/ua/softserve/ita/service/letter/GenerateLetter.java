package ua.softserve.ita.service.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.*;
import ua.softserve.ita.model.profile.Person;

@Service("generateService")
public class GenerateLetter {

    @Autowired
    LetterService letterService;

    public void sendValidationEmail(User user, String linkOfValidation){
        Letter letter = new Letter();

        letter.setEMail(user.getLogin());
        letter.setSubject("Ragistration on website RabotyNet");
        String validationLink=linkOfValidation;
        String content = "Your mail has been specified for registration on the site of RabotyNET " +
                "to complete the registration by clicking on the link:" + validationLink +
                " If you do not know about this, ignore this message;";
        letter.setContent(content);
        letter.setWithAttachment(false);

        letterService.sendLetter(letter);

    }

    public void sendRestoreForgotPasswordEmail(User user, String linkOfValidation){
        Letter letter = new Letter();

        letter.setEMail(user.getLogin());
        letter.setSubject("Restore password on website RabotyNet");
        String validationLink=linkOfValidation;
        String content = "Your mail has been specified for restore password on the site of RabotyNET " +
                "to complete the restore password by clicking on the link:" + validationLink +
                " If you do not know about this, ignore this message;";
        letter.setContent(content);
        letter.setWithAttachment(false);

        letterService.sendLetter(letter);

    }

    public void sendPersonEmail(Person person){
        Letter letter = new Letter();

        letter.setEMail(person.getContact().getEmail());
        letter.setSubject("Hello on ");
        String someLink="someLink";
        String content = "some text" + someLink;
        letter.setContent(content);
        letter.setWithAttachment(false);

        letterService.sendLetter(letter);

    }
    public void sendVacancyEmail(Vacancy vacancy){
        Letter letter = new Letter();

        letter.setEMail("chornevich.A@gmail.com");
        letter.setSubject("Hello on ");
        String someLink="someLink";
        String content = "some text" + someLink;
        letter.setContent(content);
        letter.setWithAttachment(false);

        letterService.sendLetter(letter);

    }

    public void sendPersonWithAttachment(Person person, String linkToAttachment){
        Letter letter = new Letter();

        letter.setEMail(person.getContact().getEmail());
        letter.setSubject("Hello on ");
        String validationLink="someLink";
        String content = "some text";
        letter.setContent(content);
        letter.setWithAttachment(true);

        letter.setLinkForAttachment(linkToAttachment);

        letterService.sendLetter(letter);

    }

    public void sendPersonPDF(Person person, String path){
        Letter letter = new Letter();

        letter.setEMail(person.getContact().getEmail());
        letter.setSubject("First PDF");
        String content = "some text";
        letter.setContent(content);
        letter.setWithAttachment(true);
        letter.setLinkForAttachment(path);

        letterService.sendLetter(letter);
    }

    public void sendCompanyApprove(Company company, String linkToAttachment){
        Letter letter = new Letter();

        letter.setEMail(company.getContact().getEmail());
        letter.setSubject("Approving company " + company.getName() + " on website RabotyNET");
        letter.setWithAttachment(false);
        letter.setContent("This email has benn specified as " + company.getName() + " company email.\n" +
                "Company " + company.getName() + " has been approved by RabotyNET admin.\n" +
                "To complete the approving visit the next link " + linkToAttachment);

        letterService.sendLetter(letter);
    }


}
