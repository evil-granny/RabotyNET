package ua.softserve.ita.service.profile;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Photo;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component("photoService")
@org.springframework.stereotype.Service
@Transactional
public class PhotoService implements Service<Photo> {

    private static final String uploadDirectory = System.getProperty("user.dir") + "\\uploads";

    @Resource(name = "photoDao")
    private Dao<Photo> personDao;


    @Override
    public Photo findById(Long id) {
        return personDao.findById(id);
    }

    @Override
    public List<Photo> findAll() {
        return personDao.findAll();
    }

    @Override
    public Photo create(Photo photo) {
        return personDao.create(photo);
    }

    @Override
    public Photo update(Photo photo) {
        return personDao.update(photo);
    }

    @Override
    public void deleteById(Long id) {
        personDao.deleteById(id);
    }

    public byte[] load(String fileName) throws Exception {
        Path photo = Paths.get(uploadDirectory).resolve(fileName);

        InputStream inputStream = new FileInputStream(photo.toFile());

        return IOUtils.toByteArray(inputStream);
    }

    public void upload(MultipartFile file) throws Exception {
        Files.createDirectory(Paths.get(uploadDirectory));

        Path path = Paths.get(uploadDirectory, file.getOriginalFilename());

        Files.write(path, file.getBytes());

        Photo photo = new Photo();
        photo.setName(file.getOriginalFilename());
        create(photo);
    }

}
