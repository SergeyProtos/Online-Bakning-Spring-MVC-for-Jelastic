package MyApp.dao;

import MyApp.model.Customer;
import MyApp.model.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class EventDAOImpl implements EventDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public void createEvent (String login, String message) {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        String currentDate = sdf.format(date);
        Event event = new Event(message, currentDate);
        customer.addEvent(event);
        event.setCustomer(customer);
        entityManager.persist(event);
        entityManager.persist(customer);
    }

    @Override
    public List<Event> findEventsByLogin(String login) {

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();
        List<Event> events = customer.getEvents();
        return events;
    }
}
