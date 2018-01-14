package MyApp.dao;

public interface InterestRatesDAO {

    void setInterestRatesOnDeposits();
    void setInterestRatesOnCredits();
    double findInterestRateOnDeposit(double currency, int term);
    double findInterestRateOnCredit(double currency, int term);
}
