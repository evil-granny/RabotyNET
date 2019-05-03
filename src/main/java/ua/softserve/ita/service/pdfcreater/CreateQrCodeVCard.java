package ua.softserve.ita.service.pdfcreater;

import net.glxn.qrgen.core.image.ImageType;
        import net.glxn.qrgen.core.vcard.VCard;
        import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.Person;

import java.io.*;


@Service("createQR")
public class CreateQrCodeVCard {

    public void createQRCode(Person person, String url){
        VCard vCard = new VCard();
        vCard.setName(person.getFirstName() + " " +person.getLastName());
        StringBuffer address = new StringBuffer(person.getAddress().getCountry())
                .append(", ")
                .append(person.getAddress().getCity())
                .append(", ")
                .append(person.getAddress().getStreet())
                .append(", ")
                .append(person.getAddress().getBuilding())
                .append(", ")
                .append(person.getAddress().getApartment())
                .append(", ")
                .append(person.getAddress().getZipCode());
       // vCard.setAddress(address.toString());
        //vCard.setCompany("company Inc.");
        vCard.setPhoneNumber(person.getContact().getPhoneNumber());
        //vCard.setTitle("title");
        vCard.setEmail(person.getContact().getEmail());
        vCard.setWebsite(url);


        ByteArrayOutputStream bout =
                QRCode.from(vCard)
                        .withSize(250, 250)
                        .to(ImageType.PNG)
                        .stream();

        try {
            OutputStream out = new FileOutputStream("/home/oleksandr/Documents/TestWithPDFBox/TESTCVqrcode.png");
            bout.writeTo(out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}