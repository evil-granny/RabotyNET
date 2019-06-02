package ua.softserve.ita.service;

import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.model.profile.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoService {

    Optional<Photo> findById(Long id);

    List<Photo> findAll();

    byte[] loadAvatar(Long id);

    byte[] loadLogo(Long id);

    Photo save(Photo photo);

    Photo uploadAvatar(MultipartFile file, Long userId);

    Photo uploadLogo(MultipartFile file, String companyName);

    Photo update(Photo photo);

    void deleteById(Long id);

}
