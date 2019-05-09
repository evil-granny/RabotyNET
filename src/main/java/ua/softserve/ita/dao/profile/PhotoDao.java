package ua.softserve.ita.dao.profile;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Photo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("photoDao")
@Repository
public class PhotoDao implements Dao<Photo> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Photo findById(Long id) {
        return sessionFactory.getCurrentSession().get(Photo.class, id);
    }

    @Override
    public List<Photo> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Photo> criteriaQuery = criteriaBuilder.createQuery(Photo.class);
        Root<Photo> root = criteriaQuery.from(Photo.class);
        criteriaQuery.select(root);
        Query<Photo> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Photo create(Photo photo) {
        sessionFactory.getCurrentSession().save(photo);

        return photo;
    }

    @Override
    public Photo update(Photo photo) {
        Session session = sessionFactory.getCurrentSession();

        session.update(photo);
        session.flush();

        return photo;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        Photo photo = session.byId(Photo.class).load(id);
        session.delete(photo);
    }

}
