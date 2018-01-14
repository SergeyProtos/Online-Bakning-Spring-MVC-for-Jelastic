package MyApp.controllers;

import MyApp.model.Account;
import MyApp.model.Deposit;
import MyApp.services.BankService;
import MyApp.dao.EventDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class DepositController {

    @Autowired
    private BankService bankService;

    @Autowired
    private EventDAO eventDAO;

    @RequestMapping(value = "/main/open_deposit", method = RequestMethod.POST)
    public String openDeposit(Model model, @RequestParam String login, @RequestParam String accountID,
                              @RequestParam String currency, @RequestParam String amount, @RequestParam String term) {

        if (!currency.equals("") && !amount.equals("") && !term.equals("") && !login.equals("") && !accountID.equals("")) {
            int id = Integer.parseInt(accountID);
            int currencyInt = Integer.parseInt(currency);
            double amountDouble = Double.parseDouble(amount);
            int termInt = Integer.parseInt(term);

            double availableAmount = 0;
            int availableCurrency = 0;
            List<Account> accounts = bankService.findAccountsByLogin(login);
            for (Account account: accounts) {
                if (id == account.getId()) {
                    availableAmount = account.getAmount();
                    availableCurrency = account.getCurrency();
                }
            }

            double interestRate = bankService.findInterestRateOnDeposit(currencyInt, termInt);

            Date date0 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String dateOpen = sdf.format(date0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date0);
            calendar.add(Calendar.DAY_OF_YEAR, termInt);
            Date date1 = calendar.getTime();
            String dateClose = sdf.format(date1);

            if (availableCurrency == currencyInt && availableAmount >= amountDouble) {
                Deposit deposit = new Deposit(currencyInt, amountDouble, termInt, interestRate, dateOpen, dateClose);
                bankService.openDeposit(deposit, login);
                bankService.addFundsOnAccount(id,-amountDouble);
                eventDAO.createEvent(login, "Deposit in amount " + amount + " in currency " + currency +
                        " has been opened successfully.");
                model.addAttribute("message", "Deposit has been opened successfully.");
                model.addAttribute("status", 1);
            } else {
                model.addAttribute("message", "Deposit opening error. Wrong currency or lack of funds on account.");
                model.addAttribute("status", 0);
            }
        } else {
            model.addAttribute("message", "Deposit opening error. You've left empty fields.");
            model.addAttribute("status", 0);
        }

        model.addAttribute("login", login);
        model.addAttribute("accounts", bankService.findAccountsByLogin(login));
        model.addAttribute("totalAmount", bankService.totalAmount(login));
        model.addAttribute("deposits", bankService.findDepositsByLogin(login));
        model.addAttribute("credits", bankService.findCreditsByLogin(login));
        model.addAttribute("events", eventDAO.findEventsByLogin(login));
        model.addAttribute("usd", bankService.getUsd());
        model.addAttribute("eur", bankService.getEur());
        return "main";
    }

    @RequestMapping(value = "/main/close_deposit", method = RequestMethod.POST)
    public String openDeposit(Model model, @RequestParam String login, @RequestParam String depositId, String accountId) {

        if (!depositId.equals("") && !login.equals("") && !accountId.equals("")) {
            int depositID = Integer.parseInt(depositId);
            int accountID = Integer.parseInt(accountId);
            double depositAmount = 0;
            int depositCurrency = 0;
            int accountCurrency = 0;

            List <Deposit> deposits = bankService.findDepositsByLogin(login);
            for (Deposit deposit: deposits) {
                if (depositID == deposit.getId()) {
                 depositAmount = deposit.getAmount();
                 depositCurrency = deposit.getCurrency();
                }
            }

            List<Account> accounts = bankService.findAccountsByLogin(login);
            for (Account account: accounts) {
                if (accountID == account.getId()) {
                    accountCurrency = account.getCurrency();
                }
            }

            double interest = bankService.depositInterestCalculation(depositID, login);

            if (depositCurrency == accountCurrency) {
                bankService.addFundsOnAccount(accountID, depositAmount);
                bankService.addFundsOnAccount(accountID, interest);
                bankService.closeDeposit(depositID, login);
                eventDAO.createEvent(login, "Deposit in amount " + depositAmount + " in currency " +
                        depositCurrency + " has been closed successfully. Interest on deposit = " + interest +
                        ". Deposit amount and interest amount was transferred on account #" + accountId);
                model.addAttribute("message", "Deposit has been closed successfully.");
                model.addAttribute("status", 1);
            } else {
                model.addAttribute("message", "Deposit closing error. Wrong account currency.");
                model.addAttribute("status", 0);
            }
        } else {
            model.addAttribute("message", "Deposit closing error. You've left empty fields.");
            model.addAttribute("status", 0);
        }

        model.addAttribute("login", login);
        model.addAttribute("accounts", bankService.findAccountsByLogin(login));
        model.addAttribute("totalAmount", bankService.totalAmount(login));
        model.addAttribute("deposits", bankService.findDepositsByLogin(login));
        model.addAttribute("credits", bankService.findCreditsByLogin(login));
        model.addAttribute("events", eventDAO.findEventsByLogin(login));
        model.addAttribute("usd", bankService.getUsd());
        model.addAttribute("eur", bankService.getEur());
        return "main";
    }
}
