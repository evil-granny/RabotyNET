package ua.softserve.ita.service.pdfcreater;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.model.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


//@Service("createMyPDF")
@Service
@Data
public class CreateCVPDF {

    @Autowired
    CreateQrCodeVCard createQR;

    final float BORDER_LEFT = 60;
    final float BORDER_RIGHT = 20;
    final float BORDER_UPPER = 20;
    final float BORDER_LOWER = 20;

    final int PHOTO_SIZE = 150;
    final int LOGO_SIZE_HEIGHT = 50;
    final int QR_CODE_SIZE = 35;

    final PDType1Font TITLE_FONT = PDType1Font.HELVETICA;
    final float TITLE_FONT_SIZE = 25f;
    final float TITLE_LEADING = 30f;

    final PDType1Font SUBTITLE_FONT = PDType1Font.HELVETICA;
    final float SUBTITLE_FONT_SIZE = 20f;
    final float SUBTITLE_LEADING = 25f;

    final PDType1Font CONTEXT_FONT = PDType1Font.COURIER;
    final float CONTEXT_FONT_SIZE = 14f;
    final float CONTEXT_LEADING = 16f;

    final PDType1Font CV_FORM_FONT = PDType1Font.TIMES_ITALIC;
    final float CV_FORM_FONT_SIZE = 14f;
    final float CV_FORM_LEADING = 16f;
    final Color CV_FORM_FONT_COLOR = Color.RED;

    final PDType1Font INFO_FONT = PDType1Font.COURIER_OBLIQUE;
    final float INFO_FONT_SIZE = 14f;
    final float INFO_LEADING = 16f;

    final float LEADING_LINE = 10f;

    final String SAVE_DIRECTORY_FOR_PDF_DOC = "pdf/cvPDF/cvPDFdocument.pdf";

    private PDDocument document;
    private PDPage page;
    private PDPageContentStream contentStream;
    private float yCoordinate;
    private float xCoordinate;

    public int countDescriptionLine(String description) {
        try {
            int descriptionLength = description.length();
            int countLineForDescription = 1;
            float size = CONTEXT_FONT_SIZE * CONTEXT_FONT.getStringWidth(("Description" + description)) / 1000;
            float maxSize = page.getMediaBox().getWidth();
            maxSize -= BORDER_LEFT;
            maxSize -= BORDER_RIGHT;
            if (size < maxSize) {
                return countLineForDescription;
            } else {
                float sizeLongDescription = CONTEXT_FONT_SIZE * CONTEXT_FONT.getStringWidth(description) / 1000;
                float sizeOneChar = size / descriptionLength;
                int maxLength = (int) (maxSize / sizeOneChar);
                if ((descriptionLength % maxLength) == 0) {
                    return countLineForDescription += descriptionLength / maxLength;
                } else {
                    return countLineForDescription += (descriptionLength / maxLength) + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void printContext(String formTitle, String context) {
        try {
            this.contentStream.setFont(INFO_FONT, INFO_FONT_SIZE);
            this.contentStream.setNonStrokingColor(CV_FORM_FONT_COLOR);
            this.contentStream.setLeading(INFO_LEADING);
            this.contentStream.showText(formTitle + ": ");
            this.contentStream.setNonStrokingColor(Color.BLACK);
            this.contentStream.setFont(CONTEXT_FONT, CONTEXT_FONT_SIZE);
            int contextLength = context.length();
            int formTitleLength = formTitle.length();
            float size = CONTEXT_FONT_SIZE * CONTEXT_FONT.getStringWidth((context + formTitle)) / 1000;
            float maxSize = page.getMediaBox().getWidth();
            maxSize -= BORDER_LEFT;
            maxSize -= BORDER_RIGHT;
            if (size < maxSize) {
                this.contentStream.showText(context);
                this.contentStream.newLine();
                this.yCoordinate -= INFO_LEADING;
            } else {
                this.contentStream.newLine();
                this.yCoordinate -= INFO_LEADING;
                float sizeNewLine = CONTEXT_FONT_SIZE * CONTEXT_FONT.getStringWidth(context) / 1000;
                float sizeOneChar = sizeNewLine / contextLength;
                int maxLength = (int) (maxSize / sizeOneChar);

                String[] contextArr = context.split(" ");

                List<String> listContext = new ArrayList<String>();
                StringBuilder buildLine = new StringBuilder();

                for (String word : contextArr) {

                    if (word.length() + buildLine.length() < maxLength) {
                        buildLine.append(word)
                                .append(" ");
                    } else {
                        listContext.add(buildLine.toString());
                        buildLine.delete(0, buildLine.length() - 1);
                    }
                    ;
                }

                for (String line : listContext) {
                    this.contentStream.showText(line);
                    this.contentStream.newLine();
                    this.yCoordinate -= LEADING_LINE;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void printContext(String formTitle, LocalDate beginDate, LocalDate endDate) {
        try {

            this.contentStream.setFont(INFO_FONT, INFO_FONT_SIZE);
            this.contentStream.setNonStrokingColor(CV_FORM_FONT_COLOR);
            this.contentStream.showText(formTitle + ": ");
            this.contentStream.setNonStrokingColor(Color.BLACK);
            this.contentStream.setFont(CONTEXT_FONT, CONTEXT_FONT_SIZE);
            StringBuffer educationPeriod = new StringBuffer(beginDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .append(" : ")
                    .append(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            this.contentStream.showText(educationPeriod.toString());
            this.contentStream.newLine();
            this.yCoordinate -= INFO_LEADING;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void drawLine() {

        try {
            this.contentStream.moveTo(xCoordinate, yCoordinate);
            xCoordinate += page.getMediaBox().getUpperRightX() - BORDER_LEFT - BORDER_RIGHT;
            this.contentStream.lineTo(xCoordinate, yCoordinate);
            this.contentStream.stroke();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void drawDoubleLine() {

        drawLine();

        this.yCoordinate -= LEADING_LINE / 4;
        this.xCoordinate = BORDER_LEFT;

        drawLine();
    }


    public void drawLogo() {


        String pathLogo = null;

        try {
            pathLogo = Paths.get(CreateCVPDF.class.getClassLoader().getResource("linux-icon.png").toURI()).toString();
            PDImageXObject pdLogo = PDImageXObject.createFromFile(pathLogo, document);
            float scaleLogo = (float) LOGO_SIZE_HEIGHT / pdLogo.getHeight();
            this.yCoordinate = page.getMediaBox().getLowerLeftY();
            this.yCoordinate += BORDER_LOWER;
            this.xCoordinate = page.getMediaBox().getLowerLeftX();
            this.xCoordinate += BORDER_LEFT;


            contentStream.drawImage(pdLogo, this.xCoordinate, this.yCoordinate,
                    pdLogo.getWidth() * scaleLogo, LOGO_SIZE_HEIGHT);


        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    public void createNewPage() {
        try {
            this.page = new PDPage(PDRectangle.A4);
            this.document.addPage(this.page);
            this.contentStream = new PDPageContentStream(this.document, this.page);
            drawLogo();
            this.yCoordinate = this.page.getMediaBox().getUpperRightY() - BORDER_UPPER;
            this.xCoordinate = this.page.getMediaBox().getUpperRightX() - BORDER_RIGHT;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Path createPDF(CV cv) {
        try {

            this.document = new PDDocument();
//        this.page = new PDPage(PDRectangle.A4);
//        this.document.addPage(this.page);
//        drawLogo();
//        this.contentStream = new PDPageContentStream(this.document, this.page);
//        this.yCoordinate = this.page.getMediaBox().getUpperRightY() - BORDER_UPPER;
//        this.xCoordinate = this.page.getMediaBox().getUpperRightX() - BORDER_RIGHT;

            String pathImage = "/home/oleksandr/Documents/images.jpeg";

            createNewPage();

            //photo
            //  String pathImage = "/home/oleksandr/Documents/images.jpeg";
            PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, this.document);
            float scale = (float) PHOTO_SIZE / pdImage.getWidth();
            this.yCoordinate -= pdImage.getHeight() * scale;
            this.xCoordinate -= pdImage.getWidth() * scale;
            this.contentStream.drawImage(pdImage, this.xCoordinate, this.yCoordinate,
                    pdImage.getWidth() * scale, pdImage.getHeight() * scale);

            //line
            final float X_CORDINAT_PHOTO = this.xCoordinate;
            final float Y_CORDINAT_PHOTO = this.yCoordinate;

            this.yCoordinate -= LEADING_LINE;
            this.xCoordinate = BORDER_LEFT;

            drawDoubleLine();

            final float Y_CORDINAT_TITLE_BORDER_LINE = this.yCoordinate;

//TITLE
            contentStream.beginText();
            yCoordinate = Y_CORDINAT_PHOTO + TITLE_FONT_SIZE + TITLE_LEADING;
            xCoordinate = BORDER_LEFT;
            contentStream.newLineAtOffset(xCoordinate, yCoordinate);
            contentStream.setFont(TITLE_FONT, TITLE_FONT_SIZE);
            contentStream.setLeading(TITLE_LEADING);
            contentStream.showText(cv.getPerson().getFirstName());
            contentStream.newLine();
            contentStream.showText(cv.getPerson().getLastName());
            contentStream.endText();

            //info
            contentStream.beginText();
            yCoordinate = Y_CORDINAT_TITLE_BORDER_LINE - LEADING_LINE - INFO_LEADING;
            xCoordinate = X_CORDINAT_PHOTO - PHOTO_SIZE;

            contentStream.newLineAtOffset(xCoordinate, yCoordinate);
            contentStream.setFont(INFO_FONT, INFO_FONT_SIZE);
            contentStream.setLeading(INFO_LEADING);
            contentStream.showText(cv.getPosition());
            contentStream.newLine();
            String phoneNumber = cv.getPerson().getContact().getPhoneNumber();
            printContext("Phone", phoneNumber);
            String eMail = cv.getPerson().getContact().getEmail();
            printContext("EMail", eMail);
            contentStream.endText();

            //Context

            Education education = cv.getEducation();
            Set<Job> jobs = cv.getJobs();
            Set<Skill> skills = cv.getSkills();

            float startContext = (float) 2 / 3;
            yCoordinate = page.getMediaBox().getHeight() * startContext;
            xCoordinate = BORDER_LEFT;

            contentStream.beginText();
            contentStream.newLineAtOffset(xCoordinate, yCoordinate);
            contentStream.setFont(SUBTITLE_FONT, SUBTITLE_FONT_SIZE);
            contentStream.showText("EDUCATION");
            contentStream.endText();
            final float Y_COORDINAT_SUBTITLE_EDUCATION = yCoordinate;
            yCoordinate -= LEADING_LINE;
            xCoordinate = BORDER_LEFT;

            drawDoubleLine();

            final float Y_CORDINAT_EDUCATION_BORDER_LINE = yCoordinate;

            //createQR.createQRCode(cv, "");
/////
            //String pathQR = "CVqrcode.png";
            //Path pathQR = createQR.createQRCode(cv, "");

            //createQR.createQRCode(cv, "");

            PDImageXObject pdQR = PDImageXObject.createFromByteArray(document, createQR.createQRCode(cv, "").toByteArray(), "");


            this.yCoordinate = Y_COORDINAT_SUBTITLE_EDUCATION;
            this.yCoordinate += SUBTITLE_FONT_SIZE;

            this.xCoordinate = BORDER_LEFT;
            float endQrCode = Y_CORDINAT_TITLE_BORDER_LINE - LEADING_LINE;
            float qrSize = endQrCode - this.yCoordinate;


            contentStream.drawImage(pdQR, this.xCoordinate, this.yCoordinate, qrSize, qrSize);


            //loop for education
            yCoordinate = Y_CORDINAT_EDUCATION_BORDER_LINE;
            yCoordinate -= LEADING_LINE;
            yCoordinate -= INFO_LEADING;
            xCoordinate = BORDER_LEFT;
            contentStream.beginText();
            contentStream.newLineAtOffset(xCoordinate, yCoordinate);
            contentStream.setLeading(INFO_LEADING);
            printContext("Degree", education.getDegree());
            printContext("School", education.getSchool());
            printContext("Graduation", education.getGraduation().toString());
            contentStream.endText();

            xCoordinate = BORDER_LEFT;
            yCoordinate -= LEADING_LINE;
            yCoordinate -= SUBTITLE_LEADING;
            contentStream.beginText();
            contentStream.newLineAtOffset(xCoordinate, yCoordinate);
            contentStream.setFont(SUBTITLE_FONT, SUBTITLE_FONT_SIZE);
            contentStream.showText("JOBS");
            contentStream.endText();

            yCoordinate -= LEADING_LINE;
            xCoordinate = BORDER_LEFT;

            drawDoubleLine();

            int countLineForBlock = 4;
            for (Job job : jobs) {
                if (job.getPrintPdf()) {
                    yCoordinate -= LEADING_LINE;
                    countLineForBlock += countDescriptionLine(job.getDescription());
                    float countSizeForBlock = countLineForBlock * INFO_LEADING;
                    if (((this.yCoordinate - countSizeForBlock) < BORDER_LOWER + LOGO_SIZE_HEIGHT)) {
                        this.contentStream.close();
                        createNewPage();
                    }

                    yCoordinate -= INFO_LEADING;
                    xCoordinate = BORDER_LEFT;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xCoordinate, yCoordinate);
                    contentStream.setLeading(INFO_LEADING);
                    printContext("Position", job.getPosition());

                    printContext("Period", job.getBegin(), job.getEnd());

                    printContext("Company", job.getCompanyName());

                    printContext("Description", job.getDescription());

                    yCoordinate -= INFO_LEADING;
                    xCoordinate = BORDER_LEFT;
                    contentStream.endText();
                    drawLine();
                }
            }


            yCoordinate -= LEADING_LINE;
            yCoordinate -= SUBTITLE_LEADING;
            xCoordinate = BORDER_LEFT;

            float checkYCoordinate = yCoordinate;
            checkYCoordinate -= LEADING_LINE;
            checkYCoordinate -= LEADING_LINE / 4;

            if (((checkYCoordinate) < BORDER_LOWER + LOGO_SIZE_HEIGHT)) {
                this.contentStream.close();
                createNewPage();
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(xCoordinate, yCoordinate);
            contentStream.setFont(SUBTITLE_FONT, SUBTITLE_FONT_SIZE);
            contentStream.showText("SKILLS");
            contentStream.endText();
            final float Y_COORDINAT_SUBTITLE_SKILLS = yCoordinate;
            yCoordinate -= LEADING_LINE;
            xCoordinate = BORDER_LEFT;

            drawDoubleLine();

            countLineForBlock = 2;
            for (Skill skill : skills) {
                if (skill.getPrintPdf()) {


                    yCoordinate -= LEADING_LINE;

                    countLineForBlock += countDescriptionLine(skill.getDescription());

                    float countSizeForBlock = countLineForBlock * INFO_LEADING;

                    if (((this.yCoordinate - countSizeForBlock) < BORDER_LOWER + LOGO_SIZE_HEIGHT)) {
                        this.contentStream.close();
                        createNewPage();
                    }

                    yCoordinate -= INFO_LEADING;

                    xCoordinate = BORDER_LEFT;

                    contentStream.beginText();

                    contentStream.newLineAtOffset(xCoordinate, yCoordinate);

                    contentStream.setLeading(INFO_LEADING);

                    printContext("Title", skill.getTitle());

                    printContext("Description", skill.getDescription());

                    yCoordinate -= INFO_LEADING;

                    xCoordinate = BORDER_LEFT;

                    contentStream.endText();

                    drawLine();
                }
            }

            contentStream.close();

            Path tempCVFile = Files.createTempFile("pdfCV", ".pdf");

            document.save(tempCVFile.toFile());
            System.out.println(tempCVFile.toFile());


            document.close();
            System.out.println(tempCVFile.toAbsolutePath());


            //byte[] fileContent = Files.readAllBytes(tempCVFile.toRealPath());

            //System.out.println(fileContent);

return tempCVFile;
            //return fileContent;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}