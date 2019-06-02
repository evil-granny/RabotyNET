package ua.softserve.ita.service.pdfcreator;

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
import ua.softserve.ita.service.PdfResumeService;
import ua.softserve.ita.service.PhotoService;
import ua.softserve.ita.service.ResumeService;

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
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Data
public class CreateResumePdf {

    static final float BORDER_LEFT = 60;
    static final float BORDER_RIGHT = 20;
    static final float BORDER_UPPER = 20;
    static final float BORDER_LOWER = 20;

    static final int PHOTO_SIZE = 150;
    static final int LOGO_SIZE_HEIGHT = 50;
    static final int QR_CODE_SIZE = 35;

    static final PDType1Font TITLE_FONT = PDType1Font.HELVETICA;
    static final float TITLE_FONT_SIZE = 25f;
    static final float TITLE_LEADING = 30f;

    static final PDType1Font SUBTITLE_FONT = PDType1Font.HELVETICA;
    static final float SUBTITLE_FONT_SIZE = 20f;
    static final float SUBTITLE_LEADING = 25f;

    static final PDType1Font CONTEXT_FONT = PDType1Font.COURIER;
    static final float CONTEXT_FONT_SIZE = 14f;
    static final float CONTEXT_LEADING = 16f;

    static final PDType1Font CV_FORM_FONT = PDType1Font.TIMES_ITALIC;
    static final float CV_FORM_FONT_SIZE = 14f;
    static final float CV_FORM_LEADING = 16f;
    static final Color CV_FORM_FONT_COLOR = Color.RED;

    static final PDType1Font INFO_FONT = PDType1Font.COURIER_OBLIQUE;
    static final float INFO_FONT_SIZE = 14f;
    static final float INFO_LEADING = 16f;

    static final float LEADING_LINE = 10f;

    static final String SAVE_DIRECTORY_FOR_PDF_DOC = "pdf/tempPDFdir";

    private static final Logger LOGGER = Logger.getLogger(CreateResumePdf.class.getName());

    private final PdfResumeService pdfResumeService;
    private final ResumeService resumeService;
    private final CreateQrCodeVCard createQR;
    private final PhotoService photoService;

    private PDDocument document;
    private PDPage page;
    private PDPageContentStream contentStream;
    private float yCoordinate;
    private float xCoordinate;

    @Autowired
    public CreateResumePdf(PdfResumeService pdfResumeService, ResumeService resumeService, CreateQrCodeVCard createQR, PhotoService photoService) {
        this.pdfResumeService = pdfResumeService;
        this.resumeService = resumeService;
        this.createQR = createQR;
        this.photoService = photoService;
    }

    public Path createPDF(Resume resume) {

        try {

            this.document = new PDDocument();

            createNewPage();

            try {

                Long photoId = resume.getPerson().getPhoto().getPhotoId();
                byte[] photo = photoService.loadAvatar(photoId);
                PDImageXObject pdImage = PDImageXObject.createFromByteArray(this.document, photo, "");
                float scale = (float) PHOTO_SIZE / pdImage.getHeight();
                this.yCoordinate -= pdImage.getHeight() * scale;
                this.xCoordinate -= pdImage.getWidth() * scale;
                this.contentStream.drawImage(pdImage, this.xCoordinate, this.yCoordinate,
                        pdImage.getWidth() * scale, pdImage.getHeight() * scale);

            } catch (Exception e) {

                this.yCoordinate -= PHOTO_SIZE;
                this.xCoordinate -= PHOTO_SIZE;
            }

            final float X_CORDINAT_PHOTO = this.xCoordinate;
            final float Y_CORDINAT_PHOTO = this.yCoordinate;

            this.yCoordinate -= LEADING_LINE;
            this.xCoordinate = BORDER_LEFT;

            drawDoubleLine();
            final float Y_CORDINAT_TITLE_BORDER_LINE = this.yCoordinate;
            this.contentStream.beginText();

            this.yCoordinate = Y_CORDINAT_PHOTO + TITLE_FONT_SIZE + TITLE_LEADING;
            this.xCoordinate = BORDER_LEFT;

            this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);
            this.contentStream.setFont(TITLE_FONT, TITLE_FONT_SIZE);
            this.contentStream.setLeading(TITLE_LEADING);
            this.contentStream.showText(resume.getPerson().getFirstName());
            this.contentStream.newLine();
            this.contentStream.showText(resume.getPerson().getLastName());
            this.contentStream.endText();

            this.contentStream.beginText();

            this.yCoordinate = Y_CORDINAT_TITLE_BORDER_LINE - LEADING_LINE - INFO_LEADING;
            this.xCoordinate = X_CORDINAT_PHOTO - PHOTO_SIZE;

            this.contentStream.newLineAtOffset(this.xCoordinate, this.yCoordinate);
            this.contentStream.setFont(INFO_FONT, INFO_FONT_SIZE);
            this.contentStream.setLeading(INFO_LEADING);
            this.contentStream.showText(resume.getPosition());
            this.contentStream.newLine();

            String phoneNumber = resume.getPerson().getContact().getPhoneNumber();
            printContext("Phone", phoneNumber);
            String eMail = resume.getPerson().getContact().getEmail();
            printContext("EMail", eMail);

            this.contentStream.endText();

            Education education = resume.getEducation();
            Set<Job> jobs = resume.getJobs();
            Set<Skill> skills = resume.getSkills();

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
            PDImageXObject pdQR = PDImageXObject.createFromByteArray(this.document, createQR.createQRCode(resume).toByteArray(), "");

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
            if (education.getSpecialty() == null) {
                printContext("Specialty", "");
            } else {
                printContext("Specialty", education.getSpecialty().toString());
            }
            if (education.getGraduation() == null) {
                printContext("Graduation", "");
            } else {
                printContext("Graduation", education.getGraduation().toString());
            }
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
                int countLineForBlock = 4;

                for (Job job : jobs) {

                    if (job.getPrintPdf()) {

                        this.yCoordinate -= LEADING_LINE;
                        if (job.getDescription() == null) {
                            countLineForBlock += 1;
                        } else {
                            countLineForBlock += countDescriptionLine(job.getDescription());
                        }

                        experienceHeader(countLineForBlock);
                        printContext("Position", job.getPosition());
                        printContext(job.getBegin(), job.getEnd());

                        if (job.getCompanyName() == null) {
                            printContext("Company", "");
                        } else {
                            printContext("Company", job.getCompanyName());
                        }

                        if (job.getDescription() == null) {
                            printContext("Description", "");
                        } else {
                            printContext("Description", job.getDescription());
                        }

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
                    this.yCoordinate -= SUBTITLE_LEADING;

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
                        if (skill.getDescription() == null) {
                            countLineForBlock += 1;
                        } else {
                            countLineForBlock += countDescriptionLine(skill.getDescription());
                        }

                        experienceHeader(countLineForBlock);
                        printContext("Title", skill.getTitle());

                        if (skill.getDescription() == null) {
                            printContext("Description", "");
                        } else {
                            printContext("Description", skill.getDescription());
                        }

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
            Path tempCVFile = null;

            long idUser = resume.getPerson().getUserId();
            PdfResume pdfResume = pdfResumeService.findByUserId(idUser).orElse(null);

            if (pdfResume == null) {
                pdfResume = new PdfResume();
                tempCVFile = Files.createTempFile(saveDir, "pdfCV", ".pdf");
                pdfResume.setPath(tempCVFile.toString());
                pdfResume.setPdfName(tempCVFile.getFileName().toString());
                pdfResume.setPerson(resume.getPerson());
                pdfResumeService.save(pdfResume);
            } else {

                tempCVFile = Paths.get(pdfResume.getPath());

            }

            this.document.save(tempCVFile.toFile());
            this.document.close();

            return tempCVFile;

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }
        return null;
    }

    private int countDescriptionLine(String description) {

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


    private void printContext(String formTitle, String context) {

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
                List<String> listContext = new ArrayList<>();
                StringBuilder buildLine = new StringBuilder();
                for (String word : contextArr) {

                    if (word.length() + buildLine.length() < maxLength) {
                        buildLine.append(word)
                                .append(" ");

                    } else {

                        listContext.add(buildLine.toString());
                        buildLine.delete(0, buildLine.length() - 1);
                        buildLine.append(word)
                                .append(" ");

                    }
                }

                if (buildLine.length() != 0) {
                    listContext.add(buildLine.toString());
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


    private void printContext(LocalDate beginDate, LocalDate endDate) {

        try {

            this.contentStream.setFont(INFO_FONT, INFO_FONT_SIZE);
            this.contentStream.setNonStrokingColor(CV_FORM_FONT_COLOR);
            this.contentStream.showText("Period" + ": ");
            this.contentStream.setNonStrokingColor(Color.BLACK);
            this.contentStream.setFont(CONTEXT_FONT, CONTEXT_FONT_SIZE);

            StringBuilder educationPeriod = new StringBuilder(beginDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .append(" : ")
                    .append(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

            this.contentStream.showText(educationPeriod.toString());
            this.contentStream.newLine();
            this.yCoordinate -= INFO_LEADING;

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }

    }

    private void drawLine() {

        try {

            this.contentStream.moveTo(xCoordinate, yCoordinate);
            this.xCoordinate += page.getMediaBox().getUpperRightX() - BORDER_LEFT - BORDER_RIGHT;
            this.contentStream.lineTo(xCoordinate, yCoordinate);
            this.contentStream.stroke();

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }

    }

    private void drawDoubleLine() {

        drawLine();

        this.yCoordinate -= LEADING_LINE / 4;
        this.xCoordinate = BORDER_LEFT;

        drawLine();
    }


    private void drawLogo() {

        String pathLogo = null;

        try {

            pathLogo = Paths.get(Objects.requireNonNull(CreateResumePdf.class.getClassLoader().getResource("logo.png")).toURI()).toString();
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

    private void createNewPage() {

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

    private void experienceHeader(int countLineForBlock) {

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

}

