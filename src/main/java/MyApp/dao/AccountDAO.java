package MyApp.dao;

import MyApp.model.Account;
import java.util.List;

public interface AccountDAO {

    void addAccount(Account account, String login);
    List<Account> findAccountsByLogin(String login);
    void addFundsOnAccount(int accountID, double amount);
    void transferFunds(int accountFrom, int accountTo, double amount);
    Account findAccountByID(int accountID);
    void closeAccount(int accountID, String login);
}
