package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Skill;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("skillDao")
@Repository
public class SkillDao implements Dao<Skill> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Skill findById(Long id) {
        return sessionFactory.getCurrentSession().get(Skill.class, id);
    }

    @Override
    public List<Skill> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> root = criteriaQuery.from(Skill.class);
        criteriaQuery.select(root);
        Query<Skill> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Long insert(Skill skill) {
        sessionFactory.getCurrentSession().save(skill);
        return skill.getSkillId();
    }

    @Override
    public Long update(Skill skill, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Skill updatedSkill = sessionFactory.getCurrentSession().byId(Skill.class).load(id);
        updatedSkill.setTitle(skill.getTitle());
        updatedSkill.setDescription(skill.getDescription());
        session.flush();

        return id;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Skill skill = session.byId(Skill.class).load(id);
        session.delete(skill);
    }
}
