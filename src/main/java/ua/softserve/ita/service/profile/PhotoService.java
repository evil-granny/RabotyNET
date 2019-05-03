package ua.softserve.ita.service.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class PhotoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final Path rootLocation = Paths.get("upload-dir");

    public void store(MultipartFile photo) {
        try {
            Files.copy(photo.getInputStream(), this.rootLocation.resolve(photo.getOriginalFilename()));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public Resource loadPhoto(String fileName) {
        System.out.println(rootLocation);
        try {
            Path photo = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(photo.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            logger.error(ex.getMessage());
        }

        return null;
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

}
