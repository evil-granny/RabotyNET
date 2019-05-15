package ua.softserve.ita.service;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;

public class MyTask extends TimerTask {

    private Path path;

    public MyTask(Path path){

        this.path = path;

    }

    @Override
    public void run() {
        System.out.println(path.toAbsolutePath());
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(this.path, "pdfCV" + "*")) {
            for (final Path newDirectoryStreamItem : newDirectoryStream) {
                Files.delete(newDirectoryStreamItem);
                System.out.println("clean");
            }
        } catch (final Exception e) {
            System.out.println("problem");       }



        System.out.println("Hi see you after 10 seconds");
    }

}