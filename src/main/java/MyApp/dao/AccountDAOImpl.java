package MyApp.dao;

import MyApp.model.Account;
import MyApp.model.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public void addAccount(Account account, String login) {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();
        customer.addAccount(account);
        account.setCustomer(customer);
        entityManager.persist(account);
        entityManager.persist(customer);
    }

    @Override
    public void closeAccount (int accountID, String login){
        Account account = entityManager.getReference(Account.class, accountID);
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.login =:login",
                Customer.class);
        query.setParameter("login", login);
        Customer customer = query.getSingleResult();
        customer.removeAccount(account);
        entityManager.persist(customer);
        entityManager.remove(account);
    }

    @Override
    public List<Account> findAccountsByLogin(String login) {
        List<Account> accounts = null;
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        for (Customer customer : customers) {
            String customerLogin = customer.getLogin();
            if (customerLogin.equals(login)) {
                accounts = customer.getAccounts();
            }
        }
        return accounts;
    }

    @Override
    public Account findAccountByID(int accountID) {
        return entityManager.getReference(Account.class, accountID);
    }

    @Override
    public void addFundsOnAccount(int accountID, double amount) {
        Account account = entityManager.getReference(Account.class, accountID);
        double amountBefore = account.getAmount();
        double amountAfter = amountBefore + amount;
        account.setAmount(amountAfter);
        entityManager.persist(account);
    }

    @Override
    public void transferFunds(int accountFrom, int accountTo, double amount) {
        Account from = entityManager.getReference(Account.class, accountFrom);
        Account to = entityManager.getReference(Account.class, accountTo);
        double newAmountFrom = from.getAmount() - amount;
        double newAmountTo = to.getAmount() + amount;
        from.setAmount(newAmountFrom);
        to.setAmount(newAmountTo);
        entityManager.persist(from);
        entityManager.persist(to);
    }
}

