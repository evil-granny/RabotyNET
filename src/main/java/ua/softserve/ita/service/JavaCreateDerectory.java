package ua.softserve.ita.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCreateDerectory {

    // Directory Path
    private static String dirPath ="config/subDir";

    public static void main(String[] args) throws IOException {

        // Check If Directory Already Exists Or Not?

        Path dirPathObj = Paths.get(dirPath);
        boolean dirExists = Files.exists(dirPathObj);
        if(dirExists) {
            try {
                System.out.println("! Directory Already Exists !" + dirPathObj.toRealPath() );
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                // Creating The New Directory Structure
                Files.createDirectories(dirPathObj);
                System.out.println("! New Directory Successfully Created !");
            } catch (IOException ioExceptionObj) {
                System.out.println("Problem Occured While Creating The Directory Structure= " + ioExceptionObj.getMessage());
            }
        }

        Path tempCVFile = Files.createTempFile(dirPathObj,"pdfCV", ".pdf");






    }
}
