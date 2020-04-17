package ua.com.dao.impl.profile;

import org.springframework.stereotype.Repository;
import ua.com.model.profile.Photo;
import ua.com.dao.PhotoDao;
import ua.com.dao.impl.AbstractDao;

@Repository
public class PhotoDaoImpl extends AbstractDao<Photo, Long> implements PhotoDao {
}
