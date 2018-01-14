package MyApp.dao;

import MyApp.model.Customer;
import MyApp.model.Deposit;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class DepositDAOImpl implements DepositDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public void openDeposit(Deposit deposit, String login) {

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();
        customer.addDeposit(deposit);
        deposit.setCustomer(customer);
        entityManager.persist(deposit);
        entityManager.persist(customer);
    }

    @Override
    public List<Deposit> findDepositsByLogin(String login) {

        List<Deposit> deposits = null;
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        for (Customer customer : customers) {
            String customerLogin = customer.getLogin();
            if (customerLogin.equals(login)) {
                deposits = customer.getDeposits();
            }
        }
        return deposits;
    }

    @Override
    public void closeDeposit(int id, String login) {

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();

        List<Deposit> deposits = customer.getDeposits();
        for (Deposit deposit : deposits) {
            int depositID = deposit.getId();
            if (depositID == id) {
                customer.removeDeposit(depositID);
            }
        }
        entityManager.persist(customer);
        Deposit deposit = entityManager.getReference(Deposit.class, id);
        entityManager.remove(deposit);
    }

    @Override
    public double depositInterestCalculation(int id, String login) {

        double interest = 0;
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();
        List<Deposit> deposits = customer.getDeposits();
        for (Deposit deposit : deposits) {
            int depositID = deposit.getId();
            if (depositID == id) {

                double depositAmount = deposit.getAmount();
                double interestRate = deposit.getInterestRate();

                String open = deposit.getDateOpen();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date openDate = null;
                try {
                    openDate = sdf.parse(open);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long openDateLong = 0;
                if (openDate != null) {
                    openDateLong = openDate.getTime();
                }

                Date close = new Date();
                long closeDateLong = close.getTime();

                long period = closeDateLong - openDateLong;
                int days = (int) (period / (1000 * 60 * 60 * 24));
                interest = depositAmount * interestRate / 100 * days / 365;
            }
        }
        return interest;
    }
}
