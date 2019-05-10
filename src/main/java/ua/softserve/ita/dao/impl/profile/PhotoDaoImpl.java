package ua.softserve.ita.dao.impl.profile;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.PhotoDao;
import ua.softserve.ita.dao.impl.AbstractDao;
import ua.softserve.ita.model.profile.Photo;

@Repository
public class PhotoDaoImpl extends AbstractDao<Photo, Long> implements PhotoDao {
}
