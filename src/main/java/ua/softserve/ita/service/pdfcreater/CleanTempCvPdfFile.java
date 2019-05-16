package ua.softserve.ita.service.pdfcreater;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class CleanTempCvPdfFile {

//    private static final Logger LOGGER = Logger.getLogger(CleanTempCvPdf.class.getName());
//
//    final String PREFIX_FILE_NAME = "pdfCV";
//
//    @Scheduled(cron = "0/1 * * * * *")
//    public void cleanTempFile() {
//
//        final String SAVE_DIRECTORY_FOR_PDF_DOC = "pdf/tempPDFdir";
//
//        Path path = Paths.get(SAVE_DIRECTORY_FOR_PDF_DOC);
//
//        System.out.println("Hello people");
//
//        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(path, "pdfCV" + "*")) {
//
//            for (final Path newDirectoryStreamItem : newDirectoryStream) {
//
//                Files.delete(newDirectoryStreamItem);
//
//                System.out.println("hello my cron");
//
//            }
//
//        } catch (final Exception e) {
//
//            LOGGER.log(Level.SEVERE, e.toString(), e);
//
//        }
//    }
}
