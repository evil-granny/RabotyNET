package ua.softserve.ita.service.impl.profile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.dao.PersonDao;
import ua.softserve.ita.dao.PhotoDao;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.enumtype.Extension;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public byte[] loadAsByteArray(String name) {
        try {
            Path photo = Paths.get(UPLOAD_DIRECTORY).resolve(name);
            InputStream inputStream = new FileInputStream(photo.toFile());

            return IOUtils.toByteArray(inputStream);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

        throw new RuntimeException("An error has occurred while loading photo?!");
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
    public Photo upload(MultipartFile file, Long userId) {
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));

        if (extension.equals(Extension.JPG.getType()) || extension.equals(Extension.JPEG.getType()) || extension.equals(Extension.PNG.getType())) {
            try {
                if (!Files.exists(Paths.get(UPLOAD_DIRECTORY)) || !Files.isDirectory(Paths.get(UPLOAD_DIRECTORY))) {
                    Files.createDirectory(Paths.get(UPLOAD_DIRECTORY));
                }

                UUID uuid = UUID.randomUUID();

                Path path = Paths.get(UPLOAD_DIRECTORY, uuid.toString() + extension);
                Files.write(path, file.getBytes());

                Photo photo = new Photo();
                photo.setName(uuid.toString() + extension);
                byte[] image = FileUtils.readFileToByteArray(Paths.get(UPLOAD_DIRECTORY).resolve(uuid.toString() + extension).toFile());
                photo.setImage(image);

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
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }

        throw new RuntimeException("An error has occurred while uploading photo?!");
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
