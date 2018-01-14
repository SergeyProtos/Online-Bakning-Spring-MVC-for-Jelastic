package MyApp.dao;

import MyApp.model.Courses;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class CoursesDAOImpl implements CoursesDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setCourses() {
        Courses courses = new Courses();
        entityManager.persist(courses);
    }

    @Override
    public double getUsd() {
        TypedQuery<Courses> query = entityManager.createQuery("SELECT c FROM Courses c", Courses.class);
        Courses courses = query.getSingleResult();
        double usd = courses.getUsd();
        return usd;
    }

    @Override
    public double getEur() {
        TypedQuery<Courses> query = entityManager.createQuery("SELECT c FROM Courses c", Courses.class);
        Courses courses = query.getSingleResult();
        double eur = courses.getEur();
        return eur;
    }
}
