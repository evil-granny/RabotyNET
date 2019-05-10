package ua.softserve.ita.service;

import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.model.profile.Photo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PhotoService {

    Optional<Photo> findById(Long id);

    List<Photo> findAll();

    byte[] loadAsByteArray(String name) throws IOException;

    Photo save(Photo photo);

    Photo upload(MultipartFile file, Long userId) throws IOException;

    Photo update(Photo photo);

    void deleteById(Long id);

}
