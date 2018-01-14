package MyApp.dao;

import MyApp.model.InterestRateCredit;
import MyApp.model.InterestRateDeposit;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InterestRatesDAOImpl implements InterestRatesDAO {

    @PersistenceContext (type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public void setInterestRatesOnDeposits() {
        InterestRateDeposit rateUAH1 = new InterestRateDeposit(980, 30, 12);
        InterestRateDeposit rateUAH3 = new InterestRateDeposit(980, 90, 13);
        InterestRateDeposit rateUAH6 = new InterestRateDeposit(980, 180, 14);
        InterestRateDeposit rateUAH12 = new InterestRateDeposit(980, 360, 15);
        entityManager.persist(rateUAH1);
        entityManager.persist(rateUAH3);
        entityManager.persist(rateUAH6);
        entityManager.persist(rateUAH12);
        InterestRateDeposit rateUSD1 = new InterestRateDeposit(840, 30, 2.75);
        InterestRateDeposit rateUSD3 = new InterestRateDeposit(840, 90, 3.0);
        InterestRateDeposit rateUSD6 = new InterestRateDeposit(840, 180, 3.25);
        InterestRateDeposit rateUSD12 = new InterestRateDeposit(840, 360, 3.5);
        entityManager.persist(rateUSD1);
        entityManager.persist(rateUSD3);
        entityManager.persist(rateUSD6);
        entityManager.persist(rateUSD12);
        InterestRateDeposit rateEUR1 = new InterestRateDeposit(978, 30, 2.25);
        InterestRateDeposit rateEUR3 = new InterestRateDeposit(978, 90, 2.5);
        InterestRateDeposit rateEUR6 = new InterestRateDeposit(978, 180, 2.75);
        InterestRateDeposit rateEUR12 = new InterestRateDeposit(978, 360, 3.0);
        entityManager.persist(rateEUR1);
        entityManager.persist(rateEUR3);
        entityManager.persist(rateEUR6);
        entityManager.persist(rateEUR12);
    }

    @Override
    public void setInterestRatesOnCredits() {
        InterestRateCredit rateUAH1 = new InterestRateCredit(980, 30, 18);
        InterestRateCredit rateUAH3 = new InterestRateCredit(980,90, 20);
        InterestRateCredit rateUAH6 = new InterestRateCredit(980,180, 22);
        InterestRateCredit rateUAH12 = new InterestRateCredit(980,360, 24);
        entityManager.persist(rateUAH1);
        entityManager.persist(rateUAH3);
        entityManager.persist(rateUAH6);
        entityManager.persist(rateUAH12);
        InterestRateCredit rateUSD1 = new InterestRateCredit(840, 30, 3.0);
        InterestRateCredit rateUSD3 = new InterestRateCredit(840,90, 3.25);
        InterestRateCredit rateUSD6 = new InterestRateCredit(840,180, 3.75);
        InterestRateCredit rateUSD12 = new InterestRateCredit(840,360, 4);
        entityManager.persist(rateUSD1);
        entityManager.persist(rateUSD3);
        entityManager.persist(rateUSD6);
        entityManager.persist(rateUSD12);
        InterestRateCredit rateEUR1 = new InterestRateCredit(978, 30, 4.0);
        InterestRateCredit rateEUR3 = new InterestRateCredit(978,90, 4.25);
        InterestRateCredit rateEUR6 = new InterestRateCredit(978,180, 4.75);
        InterestRateCredit rateEUR12 = new InterestRateCredit(978,360, 5);
        entityManager.persist(rateEUR1);
        entityManager.persist(rateEUR3);
        entityManager.persist(rateEUR6);
        entityManager.persist(rateEUR12);
    }

    @Override
    public double findInterestRateOnDeposit(double currency, int term) {
        double depositRate = 0;
        TypedQuery<InterestRateDeposit> query = entityManager.createQuery("SELECT c FROM InterestRateDeposit c", InterestRateDeposit.class);
        List<InterestRateDeposit> rates = query.getResultList();
        for (InterestRateDeposit rate : rates) {
            int depositCurrency = rate.getCurrency();
            int depositTerm = rate.getTerm();
            if (depositCurrency == currency && depositTerm == term) {
                depositRate = rate.getInterestRate();
            }
        }
        return depositRate;
    }

    @Override
    public double findInterestRateOnCredit(double currency, int term) {
        double creditRate = 0;
        TypedQuery<InterestRateCredit> query = entityManager.createQuery("SELECT c FROM InterestRateCredit c", InterestRateCredit.class);
        List<InterestRateCredit> rates = query.getResultList();
        for (InterestRateCredit rate : rates) {
            int creditTerm = rate.getTerm();
            int creditCurrency = rate.getCurrency();
            if (creditCurrency == currency && creditTerm == term) {
                creditRate = rate.getInterestRate();
            }
        }
        return creditRate;
    }

}
