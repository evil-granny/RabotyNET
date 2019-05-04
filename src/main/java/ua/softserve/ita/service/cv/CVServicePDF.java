package ua.softserve.ita.service.cv;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.softserve.ita.model.profile.Person;

import java.io.ByteArrayOutputStream;

public class CVServicePDF {
    private static PdfPTable table = new PdfPTable(2);

    public static ByteArrayOutputStream getPdfFile(Person person, Long id) {

        table.setWidthPercentage(60);


        putInTable("Name", person.getFirstName());

        putInTable("Last name", person.getLastName());


        putInTable("Date of birth", String.valueOf(person.getBirthday()));


        return writeToDocument();
    }

    private static void putInTable(String firstCol, String secondCol) {
        table.addCell(retrieveTable(firstCol, true));
        table.addCell(retrieveTable(secondCol, false));
    }

    private static PdfPCell retrieveTable(String colName, boolean headCell) {
        PdfPCell cell = (headCell) ? new PdfPCell(new Phrase(colName, FontFactory.getFont(FontFactory.HELVETICA_BOLD)))
                : new PdfPCell(new Phrase(colName));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        if (headCell) {
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPaddingRight(5);
        }
        return cell;
    }

    private static ByteArrayOutputStream writeToDocument() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}
