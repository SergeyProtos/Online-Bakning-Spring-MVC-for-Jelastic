package MyApp.dao;

import MyApp.model.Credit;
import java.util.List;

public interface CreditDAO {

    void getCredit(Credit credit, String login);
    List<Credit> findCreditsByLogin(String login);
    void closeCredit(int id, String login);
    double creditInterestCalculation(int id, String login);
    void repayCredit(String login, int creditID, int accountID, double amount);
}
