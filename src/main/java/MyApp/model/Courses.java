package MyApp.model;

import javax.persistence.*;

@Entity
@Table
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private double usd;

    @Column
    private double eur;

    public Courses() {
        this.usd = 28.50;
        this.eur = 34.00;
    }

    public Courses(double usd, double eur) {
        this.usd = usd;
        this.eur = eur;
    }

    public double getUsd() {
        return usd;
    }

    public double getEur() {
        return eur;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }
}
