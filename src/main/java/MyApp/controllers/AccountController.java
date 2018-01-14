package MyApp.controllers;

import MyApp.model.Account;
import MyApp.services.BankService;
import MyApp.dao.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private BankService bankService;

    @Autowired
    private EventDAO eventDAO;

    @RequestMapping(value = "/main/open_account", method = RequestMethod.POST)
    public String openAccount(Model model, @RequestParam String currency, @RequestParam String login) {

        if (!currency.equals("") && !login.equals("")) {
            int currencyInt = Integer.parseInt(currency);
            if (currencyInt == 980 || currencyInt == 840 || currencyInt == 978) {
                Account account = new Account(currencyInt);
                bankService.addAccount(account, login);
                model.addAttribute("message", "Account has been opened successfully.");
                model.addAttribute("status", 1);
                eventDAO.createEvent(login, "Account in currency " + currency + " has been opened successfully.");
            } else {
                model.addAttribute("message", "Account opening error. Wrong currency");
                model.addAttribute("status", 0);
            }
        } else {
            model.addAttribute("message", "Account opening error. Wrong login or currency");
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

    @RequestMapping(value = "/main/add_funds", method = RequestMethod.POST)
    public String addFundsOnAccount(Model model, @RequestParam String accountID, @RequestParam String amount,
                                    @RequestParam String login) {

        if (!accountID.equals("") && !amount.equals("")) {
            int id = Integer.parseInt(accountID);
            double sum = Double.parseDouble(amount);
            int currency = 0;
            boolean idChecked = false;

            List<Account> accounts = bankService.findAccountsByLogin(login);
            for (Account account : accounts) {
                int checkID = account.getId();
                if (checkID == id) {
                    idChecked = true;
                    currency = account.getCurrency();
                }
            }
            if (idChecked) {
                bankService.addFundsOnAccount(id, sum);
                model.addAttribute("message", "Funds have been added on account successfully");
                model.addAttribute("status", 1);
                eventDAO.createEvent(login, "Funds in amount " + amount + " in currency " + currency +
                        " have been added on account #" + id + " successfully.");
            } else {
                model.addAttribute("message", "Add funds failed. Wrong account ID");
                model.addAttribute("status", 0);
            }
        } else {
            model.addAttribute("message", "Add funds failed. You've assigned empty values");
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

    @RequestMapping(value = "/main/transfer_funds", method = RequestMethod.POST)
    public String transferFunds(Model model, @RequestParam String accountFrom, @RequestParam String accountTo,
                                    @RequestParam String amount, @RequestParam String login) {

        if (!accountFrom.equals("") && !accountTo.equals("") && !amount.equals("")) {

            int from = Integer.parseInt(accountFrom);
            int to = Integer.parseInt(accountTo);
            double sum = Double.parseDouble(amount);
            boolean fromChecked = false;
            boolean toChecked = false;
            int currencyFrom = 0;
            int currencyTo = 0;

            List<Account> accounts = bankService.findAccountsByLogin(login);
            for (Account account : accounts) {
                if (account.getId() == from && account.getAmount() >= sum) {
                    fromChecked = true;
                    currencyFrom = account.getCurrency();
                }
                if (account.getId() == to) {
                    toChecked = true;
                    currencyTo = account.getCurrency();
                }
            }

            if (fromChecked && toChecked && currencyFrom == currencyTo && from != to) {
                bankService.transferFunds(from, to, sum);
                eventDAO.createEvent(login, "Funds in amount " + amount + " in currency " + currencyFrom +
                        " has been transferred from account #" + from + " to account #" + to + " successfully.");
                model.addAttribute("message", "Funds has been transferred successfully.");
                model.addAttribute("status", 1);
            } else if (from == to) {
                model.addAttribute("message", "Funds transfer failed. You've specified the same account.");
                model.addAttribute("status", 0);
            } else {
                model.addAttribute("message", "Funds transfer failed. Wrong currency or lack of funds on amount.");
                model.addAttribute("status", 0);
            }
        } else {
            model.addAttribute("message", "Add funds failed. You've left empty fields");
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

    @RequestMapping(value = "/main/close_account", method = RequestMethod.POST)
    public String closeAccount(Model model, @RequestParam String accountID, @RequestParam String login) {

        int id = Integer.parseInt(accountID);
        Account account = bankService.findAccountByID(id);
        double accountAmount = account.getAmount();

        if (!accountID.equals("") && accountAmount == 0) {
            bankService.closeAccount(id, login);
            eventDAO.createEvent(login,"Account #" + id + " has been closed successfully.");
            model.addAttribute("message", "Account has been closed successfully.");
            model.addAttribute("status", 1);
        }
        else if (!accountID.equals("") && accountAmount > 0) {
            model.addAttribute("message", "Account closing failed. There is some funds on account. " +
                    "Transfer funds to another account.");
            model.addAttribute("status", 0);}
        else {
            model.addAttribute("message", "Account closing failed. You've left empty fields");
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