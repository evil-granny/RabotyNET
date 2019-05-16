package ua.softserve.ita.service.pdfcreater;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanTempCvPdf extends TimerTask {

    private static final Logger LOGGER = Logger.getLogger(CleanTempCvPdf.class.getName());

    private Path path;

    final String PREFIX_FILE_NAME = "pdfCV";

    public CleanTempCvPdf(Path path) {

        this.path = path;

    }

    @Override
    public void run() {

        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(this.path, PREFIX_FILE_NAME + "*")) {

            for (final Path newDirectoryStreamItem : newDirectoryStream) {

                Files.delete(newDirectoryStreamItem);

            }

        } catch (final Exception e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }
    }
}