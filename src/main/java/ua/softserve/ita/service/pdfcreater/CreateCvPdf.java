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
import ua.softserve.ita.model.profile.Photo;
import ua.softserve.ita.service.PhotoService;

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
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Data
public class CreateCvPdf {

    private static final Logger LOGGER = Logger.getLogger(CreateCvPdf.class.getName());

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

    final String SAVE_DIRECTORY_FOR_PDF_DOC = "pdf/tempPDFdir";

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

            LOGGER.log(Level.SEVERE, e.toString(), e);
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
                }

                for (String line : listContext) {

                    this.contentStream.showText(line);

                    this.contentStream.newLine();

                    this.yCoordinate -= LEADING_LINE;
                }
            }

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

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

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }

    }

    public void drawLine() {

        try {

            this.contentStream.moveTo(xCoordinate, yCoordinate);

            this.xCoordinate += page.getMediaBox().getUpperRightX() - BORDER_LEFT - BORDER_RIGHT;

            this.contentStream.lineTo(xCoordinate, yCoordinate);

            this.contentStream.stroke();

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

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

            pathLogo = Paths.get(CreateCvPdf.class.getClassLoader().getResource("logo.png").toURI()).toString();

            PDImageXObject pdLogo = PDImageXObject.createFromFile(pathLogo, document);

            float scaleLogo = (float) LOGO_SIZE_HEIGHT / pdLogo.getHeight();

            this.yCoordinate = page.getMediaBox().getLowerLeftY();

            this.yCoordinate += BORDER_LOWER;

            this.xCoordinate = page.getMediaBox().getLowerLeftX();

            this.xCoordinate += BORDER_LEFT;

            contentStream.drawImage(pdLogo, this.xCoordinate, this.yCoordinate,
                    pdLogo.getWidth() * scaleLogo, LOGO_SIZE_HEIGHT);

        } catch (URISyntaxException | IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

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

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }

    }

    public void experienceHeader(int countLineForBlock) {

        try {

            float countSizeForBlock = countLineForBlock * INFO_LEADING;

            if (((this.yCoordinate - countSizeForBlock) < BORDER_LOWER + LOGO_SIZE_HEIGHT)) {

                this.contentStream.close();

                createNewPage();
            }

            this.yCoordinate -= INFO_LEADING;

            this.xCoordinate = BORDER_LEFT;

            this.contentStream.beginText();

            this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);

            this.contentStream.setLeading(INFO_LEADING);

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }

    }

    public Path createPDF(CV cv) {

        try {

            this.document = new PDDocument();

            createNewPage();

            try {

                PDImageXObject pdImage = PDImageXObject.createFromByteArray(this.document,cv.getPerson().getPhoto().getImage(),"");

                float scale = (float) PHOTO_SIZE / pdImage.getWidth();

                this.yCoordinate -= pdImage.getHeight() * scale;

                this.xCoordinate -= pdImage.getWidth() * scale;

                this.contentStream.drawImage(pdImage, this.xCoordinate, this.yCoordinate,
                        pdImage.getWidth() * scale, pdImage.getHeight() * scale);

            } catch (Exception e) {

                this.yCoordinate -= PHOTO_SIZE;

                this.xCoordinate -= PHOTO_SIZE;
            }

            //line
            final float X_CORDINAT_PHOTO = this.xCoordinate;

            final float Y_CORDINAT_PHOTO = this.yCoordinate;

            this.yCoordinate -= LEADING_LINE;

            this.xCoordinate = BORDER_LEFT;

            drawDoubleLine();

            final float Y_CORDINAT_TITLE_BORDER_LINE = this.yCoordinate;

            //TITLE
            this.contentStream.beginText();

            this.yCoordinate = Y_CORDINAT_PHOTO + TITLE_FONT_SIZE + TITLE_LEADING;

            this.xCoordinate = BORDER_LEFT;

            this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);

            this.contentStream.setFont(TITLE_FONT, TITLE_FONT_SIZE);

            this.contentStream.setLeading(TITLE_LEADING);

            this.contentStream.showText(cv.getPerson().getFirstName());

            this.contentStream.newLine();

            this.contentStream.showText(cv.getPerson().getLastName());

            this.contentStream.endText();

            //info
            this.contentStream.beginText();

            this.yCoordinate = Y_CORDINAT_TITLE_BORDER_LINE - LEADING_LINE - INFO_LEADING;

            this.xCoordinate = X_CORDINAT_PHOTO - PHOTO_SIZE;

            this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);

            this.contentStream.setFont(INFO_FONT, INFO_FONT_SIZE);

            this.contentStream.setLeading(INFO_LEADING);

            this.contentStream.showText(cv.getPosition());

            this.contentStream.newLine();

            String phoneNumber = cv.getPerson().getContact().getPhoneNumber();

            printContext("Phone", phoneNumber);

            String eMail = cv.getPerson().getContact().getEmail();

            printContext("EMail", eMail);

            this.contentStream.endText();

            //Context
            Education education = cv.getEducation();

            Set<Job> jobs = cv.getJobs();

            Set<Skill> skills = cv.getSkills();

            float startContext = (float) 2 / 3;

            this.yCoordinate = page.getMediaBox().getHeight() * startContext;

            this.xCoordinate = BORDER_LEFT;

            this.contentStream.beginText();

            this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);

            this.contentStream.setFont(SUBTITLE_FONT, SUBTITLE_FONT_SIZE);

            this.contentStream.showText("EDUCATION");

            this.contentStream.endText();

            final float Y_COORDINAT_SUBTITLE_EDUCATION = this.yCoordinate;

            this.yCoordinate -= LEADING_LINE;

            this.xCoordinate = BORDER_LEFT;

            drawDoubleLine();

            final float Y_CORDINAT_EDUCATION_BORDER_LINE = this.yCoordinate;

            PDImageXObject pdQR = PDImageXObject.createFromByteArray(this.document, createQR.createQRCode(cv, "").toByteArray(), "");

            this.yCoordinate = Y_COORDINAT_SUBTITLE_EDUCATION;

            this.yCoordinate += SUBTITLE_FONT_SIZE;

            this.xCoordinate = BORDER_LEFT;

            float endQrCode = Y_CORDINAT_TITLE_BORDER_LINE - LEADING_LINE;

            float qrSize = endQrCode - this.yCoordinate;

            this.contentStream.drawImage(pdQR, this.xCoordinate, this.yCoordinate, qrSize, qrSize);

            this.yCoordinate = Y_CORDINAT_EDUCATION_BORDER_LINE;

            this.yCoordinate -= LEADING_LINE;

            this.yCoordinate -= INFO_LEADING;

            this.xCoordinate = BORDER_LEFT;

            this.contentStream.beginText();

            this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);

            this.contentStream.setLeading(INFO_LEADING);

            printContext("Degree", education.getDegree());

            printContext("School", education.getSchool());

            printContext("Graduation", education.getGraduation().toString());

            this.contentStream.endText();

            boolean printExistsJob = jobs.stream()
                    .anyMatch(t -> t.getPrintPdf().equals(true));

            if (printExistsJob) {

                this.xCoordinate = BORDER_LEFT;

                this.yCoordinate -= LEADING_LINE;

                this.yCoordinate -= SUBTITLE_LEADING;

                this.contentStream.beginText();

                this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);

                this.contentStream.setFont(SUBTITLE_FONT, SUBTITLE_FONT_SIZE);

                this.contentStream.showText("JOBS");

                this.contentStream.endText();

                this.yCoordinate -= LEADING_LINE;

                this.xCoordinate = BORDER_LEFT;

                drawDoubleLine();
//
                int countLineForBlock = 4;

                for (Job job : jobs) {

                    if (job.getPrintPdf()) {

                        this.yCoordinate -= LEADING_LINE;

                        countLineForBlock += countDescriptionLine(job.getDescription());

                        experienceHeader(countLineForBlock);

                        printContext("Position", job.getPosition());

                        printContext("Period", job.getBegin(), job.getEnd());

                        printContext("Company", job.getCompanyName());

                        printContext("Description", job.getDescription());

                        this.yCoordinate -= INFO_LEADING;

                        this.xCoordinate = BORDER_LEFT;

                        this.contentStream.endText();

                        drawLine();
                    }
                }
            }
//

            this.yCoordinate -= LEADING_LINE;

            this.yCoordinate -= SUBTITLE_LEADING;

            this.xCoordinate = BORDER_LEFT;

            float checkYCoordinate = this.yCoordinate;

            checkYCoordinate -= LEADING_LINE;

            checkYCoordinate -= LEADING_LINE / 4;

            boolean printExistsSkill = skills.stream()
                    .anyMatch(t -> t.getPrintPdf().equals(true));
            //
            if (printExistsSkill) {

                if (((checkYCoordinate) < BORDER_LOWER + LOGO_SIZE_HEIGHT)) {

                    this.contentStream.close();

                    createNewPage();

                    this.xCoordinate = this.page.getMediaBox().getLowerLeftX() + BORDER_LEFT;

                    this.yCoordinate -=  SUBTITLE_LEADING;

                }

                this.contentStream.beginText();

                this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);

                this.contentStream.setFont(SUBTITLE_FONT, SUBTITLE_FONT_SIZE);

                this.contentStream.showText("SKILLS");

                this.contentStream.endText();

                final float Y_COORDINAT_SUBTITLE_SKILLS = this.yCoordinate;

                this.yCoordinate -= LEADING_LINE;

                this.xCoordinate = BORDER_LEFT;

                drawDoubleLine();

                int countLineForBlock = 2;

                for (Skill skill : skills) {

                    if (skill.getPrintPdf()) {

                        this.yCoordinate -= LEADING_LINE;

                        countLineForBlock += countDescriptionLine(skill.getDescription());

                        experienceHeader(countLineForBlock);

                        printContext("Title", skill.getTitle());

                        printContext("Description", skill.getDescription());

                        this.yCoordinate -= INFO_LEADING;

                        this.xCoordinate = BORDER_LEFT;

                        this.contentStream.endText();

                        drawLine();
                    }
                }
            }

            //

            this.contentStream.close();

            Path saveDir = Paths.get(SAVE_DIRECTORY_FOR_PDF_DOC);

            Path tempCVFile = Files.createTempFile(saveDir, "pdfCV", ".pdf");

            this.document.save(tempCVFile.toFile());

            this.document.close();

            return tempCVFile;

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }
        return null;
    }

}
