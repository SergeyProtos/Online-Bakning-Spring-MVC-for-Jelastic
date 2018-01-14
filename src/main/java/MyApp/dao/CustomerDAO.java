package MyApp.dao;

import MyApp.model.Customer;

public interface CustomerDAO {

    void addCustomer(Customer customer);
    boolean loginExists(String login);
    boolean isLogin(String login, String password);
}
