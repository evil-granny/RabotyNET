package ua.softserve.ita.service.pdfcreator;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanTempResumePdf extends TimerTask {

    private static final Logger LOGGER = Logger.getLogger(CleanTempResumePdf.class.getName());

    private Path path;

    private static final String PREFIX_FILE_NAME = "pdfCV";

    public CleanTempResumePdf(Path path) {
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
