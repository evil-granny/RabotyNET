package ua.softserve.ita.service.impl.profile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.dao.PersonDao;
import ua.softserve.ita.dao.PhotoDao;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.model.profile.Photo;
import ua.softserve.ita.service.PhotoService;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";

    private final PhotoDao photoDao;

    private final PersonDao personDao;

    @Autowired
    public PhotoServiceImpl(PhotoDao photoDao, PersonDao personDao) {
        this.photoDao = photoDao;
        this.personDao = personDao;
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return photoDao.findById(id);
    }

    @Override
    public byte[] loadAsByteArray(String name) throws IOException {
        Path photo = Paths.get(UPLOAD_DIRECTORY).resolve(name);
        InputStream inputStream = new FileInputStream(photo.toFile());

        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public List<Photo> findAll() {
        return photoDao.findAll();
    }

    @Override
    public Photo save(Photo photo) {
        return photoDao.save(photo);
    }

    @Override
    public Photo upload(MultipartFile file, Long userId) throws IOException {
        if (!Files.exists(Paths.get(UPLOAD_DIRECTORY)) || !Files.isDirectory(Paths.get(UPLOAD_DIRECTORY))) {
            Files.createDirectory(Paths.get(UPLOAD_DIRECTORY));
        }

        UUID uuid = UUID.randomUUID();
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));

        Path path = Paths.get(UPLOAD_DIRECTORY, uuid.toString() + extension);
        Files.write(path, file.getBytes());

        byte[] fileContent = FileUtils.readFileToByteArray(Paths.get(UPLOAD_DIRECTORY).resolve(uuid.toString() + extension).toFile());
        Photo photo = new Photo();
        photo.setName(uuid.toString() + extension);
        photo.setBase64Image(Base64.getEncoder().encodeToString(fileContent));

        Photo savedPhoto = photoDao.save(photo);

        Person person = personDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("Person with id: %d was not found", userId)));

        long deletedPhotoId = -1;

        if (person.getPhoto() != null) {
            Files.delete(Paths.get(UPLOAD_DIRECTORY).resolve(person.getPhoto().getName()));

            deletedPhotoId = person.getPhoto().getPhotoId();
        }
        person.setPhoto(savedPhoto);
        personDao.update(person);

        if (deletedPhotoId != -1) {
            photoDao.deleteById(deletedPhotoId);
        }

        return savedPhoto;
    }

    @Override
    public Photo update(Photo photo) {
        return photoDao.update(photo);
    }

    @Override
    public void deleteById(Long id) {
        photoDao.deleteById(id);
    }

}
