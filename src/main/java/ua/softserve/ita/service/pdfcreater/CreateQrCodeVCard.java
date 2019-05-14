package ua.softserve.ita.service.pdfcreater;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.vcard.VCard;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.CV;

import java.io.*;

@Service("createQR")
public class CreateQrCodeVCard {

    public ByteArrayOutputStream createQRCode(CV cv, String url){
        VCard vCard = new VCard();
        vCard.setName(cv.getPerson().getFirstName() + " " +cv.getPerson().getLastName());
        StringBuffer address = new StringBuffer(cv.getPerson().getAddress().getCountry())
                .append(", ")
                .append(cv.getPerson().getAddress().getCity())
                .append(", ")
                .append(cv.getPerson().getAddress().getStreet())
                .append(", ")
                .append(cv.getPerson().getAddress().getBuilding())
                .append(", ")
                .append(cv.getPerson().getAddress().getApartment())
                .append(", ")
                .append(cv.getPerson().getAddress().getZipCode());
        // vCard.setAddress(address.toString());
        //vCard.setCompany("company Inc.");
        vCard.setPhoneNumber(cv.getPerson().getContact().getPhoneNumber());
        vCard.setTitle(cv.getPosition());
        vCard.setEmail(cv.getPerson().getContact().getEmail());
        vCard.setWebsite(url);

        ByteArrayOutputStream bout =
                QRCode.from(vCard)
                        .withSize(250, 250)
                        .to(ImageType.PNG)
                        .stream();


        return bout;

    }

}