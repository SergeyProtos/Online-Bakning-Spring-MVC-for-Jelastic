package MyApp.dao;

import MyApp.model.Deposit;
import java.util.List;

public interface DepositDAO {

    void openDeposit(Deposit deposit, String login);
    List<Deposit> findDepositsByLogin(String login);
    void closeDeposit(int id, String login);
    double depositInterestCalculation(int id, String login);
}
