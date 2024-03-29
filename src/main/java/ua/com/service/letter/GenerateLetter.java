package ua.com.service.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.model.Letter;
import ua.com.model.profile.Person;
import ua.com.model.Company;
import ua.com.model.User;

@Service("generateService")
public class GenerateLetter {

    private final LetterService letterService;

    @Autowired
    public GenerateLetter(LetterService letterService) {
        this.letterService = letterService;
    }

    public void sendValidationEmail(User user, String linkOfValidation) {
        Letter letter = new Letter();
        letter.setEmailAddress(user.getLogin());
        letter.setSubject("Registration on website RabotyNet");
        String content = "Your mail has been specified for registration on the site of RabotyNET " +
                "to complete the registration by clicking on the link:" + linkOfValidation +
                " If you do not know about this, ignore this message;";

        letter.setContent(content);
        letter.setWithAttachment(false);
        letterService.sendLetter(letter);

    }

    public void sendRestoreForgotPasswordEmail(User user, String linkOfValidation) {
        Letter letter = new Letter();
        letter.setEmailAddress(user.getLogin());
        letter.setSubject("Restore password on website RabotyNet");
        String content = "Your mail has been specified for restore password on the site of RabotyNET " +
                "to complete the restore password by clicking on the link:" + linkOfValidation +
                " If you do not know about this, ignore this message;";
        letter.setContent(content);
        letter.setWithAttachment(false);

        letterService.sendLetter(letter);

    }

    public void sendPersonPDF(Person person, String path) {
        Letter letter = new Letter();
        letter.setEmailAddress(person.getContact().getEmail());
        letter.setSubject("Your new resume from RabotyNET");
        String content = "Thank you for using our PDF designer, your Resume in attached";
        letter.setContent(content);
        letter.setWithAttachment(true);
        letter.setLinkForAttachment(path);
        letterService.sendLetter(letter);
    }

    public void sendResumePdfForVacancy(String emailAddress, String path) {

        Letter letter = new Letter();
        letter.setEmailAddress(emailAddress);
        letter.setSubject("Your get new resume from RabotyNET");
        String content = "Candidate Resume in attached";
        letter.setContent(content);
        letter.setWithAttachment(true);
        letter.setLinkForAttachment(path);
        letterService.sendLetter(letter);
    }

    public void sendCompanyApprove(Company company, String linkToAttachment) {
        Letter letter = new Letter();
        letter.setEmailAddress(company.getContact().getEmail());
        letter.setSubject("Approving company " + company.getName() + " on website RabotyNET");

        letter.setWithAttachment(false);
        letter.setContent("This email has been specified as " + company.getName() + " company email.\n" +
                "Company " + company.getName() + " has been approved by RabotyNET admin.\n" +
                "To complete the approving visit the next link " + linkToAttachment);

        letterService.sendLetter(letter);
    }

}
