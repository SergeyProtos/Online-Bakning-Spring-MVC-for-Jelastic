package MyApp.services;

import MyApp.model.Account;
import MyApp.model.Credit;
import MyApp.model.Customer;
import MyApp.model.Deposit;

import java.util.List;

public interface BankService {

    void addCustomer(Customer customer);
    boolean loginExists(String login);
    boolean isLogin(String login, String password);

    void addAccount(Account account, String login);
    void closeAccount(int accountID, String login);
    List<Account> findAccountsByLogin(String login);
    Account findAccountByID(int accountID);
    void addFundsOnAccount(int accountID, double amount);
    void transferFunds(int accountFrom, int accountTo, double amount);
    double totalAmount(String login);

    void openDeposit(Deposit deposit, String login);
    List <Deposit> findDepositsByLogin(String login);
    void closeDeposit(int id, String login);
    double depositInterestCalculation(int id, String login);

    void getCredit(Credit credit, String login);
    List <Credit> findCreditsByLogin(String login);
    void closeCredit(int id, String login);
    void repayCredit(String login, int creditID, int accountID, double amount);
    double creditInterestCalculation(int id, String login);

    void setInterestRatesOnDeposits();
    void setInterestRatesOnCredits();
    double findInterestRateOnDeposit(double currency, int term);
    double findInterestRateOnCredit(double currency, int term);

    void setCourses();
    double getUsd();
    double getEur();
}
