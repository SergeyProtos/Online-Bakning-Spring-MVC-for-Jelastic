package MyApp.dao;

import MyApp.model.Customer;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCustomer(Customer customer) {
        entityManager.merge(customer);
    }

    public boolean loginExists(String login) {
        boolean loginExists = false;
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        for (Customer customer : customers) {
            String customerLogin = customer.getLogin();
            if (customerLogin.equals(login)) {
                loginExists = true;
            }
        }
        return loginExists;
    }

    public boolean isLogin(String login, String password) {
        boolean isLogin = false;
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        for (Customer customer : customers) {
            String customerLogin = customer.getLogin();
            String customerPassword = customer.getPassword();
            if (customerLogin.equals(login) && customerPassword.equals(password)) {
                isLogin = true;
            }
        }
        return isLogin;
    }

//    public double totalAmount ()
}