package MyApp.model;

import javax.persistence.*;

@Entity
@Table
public class InterestRateDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int currency;

    @Column
    private int term;

    @Column
    private double interestRate;

    public InterestRateDeposit() {
    }

    public InterestRateDeposit(int currency, int term, double interestRate) {
        this.currency = currency;
        this.term = term;
        this.interestRate = interestRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
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
}
