package MyApp.model;

import javax.persistence.*;

@Entity
@Table
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private int currency;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private int term;

    @Column (nullable = false)
    private double interestRate;

    @Column (nullable = false)
    private String dateOpen;

    @Column (nullable = false)
    private String dateClose;

    public Deposit() {
    }

    public Deposit(int currency, double amount, int term, double interestRate, String dateOpen, String dateClose) {
        this.currency = currency;
        this.amount = amount;
        this.term = term;
        this.interestRate = interestRate;
        this.dateOpen = dateOpen;
        this.dateClose = dateClose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(String dateOpen) {
        this.dateOpen = dateOpen;
    }

    public String getDateClose() {
        return dateClose;
    }

    public void setDateClose(String dateClose) {
        this.dateClose = dateClose;
    }
}
