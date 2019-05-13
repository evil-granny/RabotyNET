package ua.softserve.ita.service.pdfcreater;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.profile.Person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;


@Service("createMyPDF")

public class  TestCVPDF {

    @Autowired
    CreateQrCodeVCard createQR;

    public void createPDF(Person person) {

        final int PHOTO_SIZE = 150;
        final int BORDER = 15;


        PDDocument document = new PDDocument();
        PDPage page = new PDPage();


        PDPageContentStream contentStream;

        try {
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.newLineAtOffset(75, 660);
            contentStream.setFont(PDType1Font.COURIER, 25);
            contentStream.showText(person.getFirstName());
            contentStream.setLeading(35f);
            contentStream.newLine();
            contentStream.showText(person.getLastName());
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset((BORDER + page.getMediaBox().getWidth() / 3) * 2, 580);
            contentStream.setFont(PDType1Font.COURIER, 14);
            contentStream.showText("Position");
            contentStream.setLeading(15f);
            contentStream.newLine();
            contentStream.showText("Phone: ");
            contentStream.showText(person.getContact().getPhoneNumber());
            contentStream.newLine();
            contentStream.showText("Email: ");
            contentStream.showText(person.getContact().getEmail());
            contentStream.endText();


            String pathImage = "/home/oleksandr/Documents/images.jpeg";
            PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, document);
            float scale = (float) PHOTO_SIZE / pdImage.getWidth();
            contentStream.drawImage(pdImage, page.getMediaBox().getWidth() - BORDER - pdImage.getWidth() * scale, page.getMediaBox().getHeight() - BORDER - pdImage.getHeight() * scale,
                    pdImage.getWidth() * scale, pdImage.getHeight() * scale);
            contentStream.drawLine(25, 600, 570, 605);

            createQR.createQRCode(person, "");

            String pathQR = "/home/oleksandr/Documents/TestWithPDFBox/TESTCVqrcode.png";
            PDImageXObject pdQR = PDImageXObject.createFromFile(pathQR, document);
            contentStream.drawImage(pdQR, 15, 15,
                    150, 150);
            //contentStream.drawLine(25, 600, 570, 605);


//
//
//                contentStream.newLineAtOffset(25, 250);
//                contentStream.setFont(PDType1Font.HELVETICA, 12);
//                contentStream.setLeading(14.5f);
//                String text="This is an example of adding text to a page in the pdf document. we can" +
//                        "add as many lines as we want like this using the showText() method of the" +
//                        "ContentStream class";
//                contentStream.showText(text);
//                contentStream.endText();
            contentStream.close();
            String pathPhoto = "/home/oleksandr/Documents/images.jpeg";

            //String pathImage = null;
            try {
                pathImage = Paths.get(TestCVPDF.class.getClassLoader().getResource("linux-icon.png").toURI()).toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            System.out.println(pathImage);

            //document = addImage(document, page, pathImage);
            document.save("/home/oleksandr/Documents/TestWithPDFBox/TESTCV.pdf");

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}