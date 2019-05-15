package ua.softserve.ita.service.pdfcreater;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.vcard.VCard;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.CV;

import java.io.*;

@Service("createQR")
public class CreateQrCodeVCard {

    final int QR_CODE_SIZE = 250;

    public ByteArrayOutputStream createQRCode(CV cv, String url){

        VCard vCard = new VCard();

        vCard.setName(cv.getPerson().getFirstName() + " " +cv.getPerson().getLastName());

        vCard.setPhoneNumber(cv.getPerson().getContact().getPhoneNumber());

        vCard.setTitle(cv.getPosition());

        vCard.setEmail(cv.getPerson().getContact().getEmail());

        ByteArrayOutputStream bout =
                QRCode.from(vCard)
                        .withSize(QR_CODE_SIZE, QR_CODE_SIZE)
                        .to(ImageType.PNG)
                        .stream();

        return bout;
    }
}