package MyApp.dao;

import MyApp.model.Event;
import java.util.List;

public interface EventDAO {

    void createEvent(String login, String message);
    List<Event> findEventsByLogin(String login);
}
