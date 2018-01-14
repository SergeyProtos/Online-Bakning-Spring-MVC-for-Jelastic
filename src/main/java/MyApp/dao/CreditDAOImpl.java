package MyApp.dao;


import MyApp.model.Credit;
import MyApp.model.Customer;
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
public class CreditDAOImpl implements CreditDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public void getCredit (Credit credit, String login) {

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();
        customer.addCredit(credit);
        credit.setCustomer(customer);
        entityManager.persist(credit);
        entityManager.persist(customer);
    }

    @Override
    public List<Credit> findCreditsByLogin(String login) {

        List<Credit> credits = null;
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        for (Customer customer : customers) {
            String customerLogin = customer.getLogin();
            if (customerLogin.equals(login)) {
                credits = customer.getCredits();
            }
        }
        return credits;
    }

    @Override
    public void closeCredit(int id, String login) {

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();

        List<Credit> credits = customer.getCredits();
        for (Credit credit : credits) {
            int creditID = credit.getId();
            if (creditID == id) {
                customer.removeCredit(creditID);
            }
        }
        entityManager.persist(customer);
        Credit credit = entityManager.getReference(Credit.class, id);
        entityManager.remove(credit);
    }

    @Override
    public void repayCredit(String login, int creditID, int accountID, double amount) {

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();

        double creditAmountBefore = 0;
        List<Credit> credits = customer.getCredits();
        for (Credit credit : credits) {
            int id = credit.getId();
            if (creditID == id) {
                creditAmountBefore = credit.getAmount();
            }
        }

        double creditAmountAfter = creditAmountBefore - amount;
        Credit credit = entityManager.getReference(Credit.class, creditID);
        credit.setAmount(creditAmountAfter);
        entityManager.persist(credit);
    }

    @Override
    public double creditInterestCalculation(int id, String login) {

        double interest = 0;
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();
        List<Credit> credits = customer.getCredits();
        for (Credit credit : credits) {
            int creditID = credit.getId();
            if (creditID == id) {

                double creditAmount = credit.getAmount();
                double interestRate = credit.getInterestRate();

                String open = credit.getDateOpen();
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
                interest = creditAmount * interestRate / 100 * days / 365;
            }
        }
        return interest;
    }
}
