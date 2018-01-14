package MyApp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Deposit> deposits = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Credit> credits = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    public Customer() {
    }

    public Customer(String login, String password, String firstName, String lastName, String email, String phone) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public void addAccount(Account account) {
        account.setCustomer(this);
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public void addDeposit(Deposit deposit) {
        deposit.setCustomer(this);
        deposits.add(deposit);
    }

    public void removeDeposit(int depositID) {
        List<Deposit> depositsNew = new ArrayList<>();
        for (Deposit deposit : deposits) {
            if (depositID != deposit.getId()) {
                depositsNew.add(deposit);
            }
        }
        deposits = depositsNew;
    }

    public void addCredit(Credit credit) {
        credit.setCustomer(this);
        credits.add(credit);
    }

    public void removeCredit(int creditID) {
        List<Credit> creditsNew = new ArrayList<>();
        for (Credit credit : credits) {
            if (creditID != credit.getId()) {
                creditsNew.add(credit);
            }
        }
        credits = creditsNew;
    }

    public void addEvent(Event event) {
        event.setCustomer(this);
        events.add(event);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
