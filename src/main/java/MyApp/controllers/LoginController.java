package MyApp.controllers;

import MyApp.model.Customer;
import MyApp.services.BankService;
import MyApp.dao.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private BankService bankService;

    @Autowired
    private EventDAO eventDAO;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam String login, @RequestParam String password) {

        String result = "";
        boolean loginExists = bankService.loginExists(login);
        boolean isLogin = bankService.isLogin(login, password);

        // checking empty data, if login exists and successful log in
        if (login.equals("") || password.equals("")) {
            result = "index";
            model.addAttribute("message", "Empty password or login. Please sign in correctly.");
        }
        if (!loginExists) {
            result = "index";
            model.addAttribute("message", "You've entered wrong login. Please sign in correctly.");
        }
        if (loginExists && !isLogin) {
            result = "index";
            model.addAttribute("message", "You've entered wrong password. Please sign in correctly.");
        }
        if (isLogin) {
            result = "main";
            eventDAO.createEvent(login, "You've singed in successfully.");
            model.addAttribute("message", "You've singed in successfully.");
            model.addAttribute("status", 1);
            model.addAttribute("login", login);
            model.addAttribute("accounts", bankService.findAccountsByLogin(login));
            model.addAttribute("totalAmount", bankService.totalAmount(login));
            model.addAttribute("deposits", bankService.findDepositsByLogin(login));
            model.addAttribute("credits", bankService.findCreditsByLogin(login));
            model.addAttribute("events", eventDAO.findEventsByLogin(login));
            model.addAttribute("usd", bankService.getUsd());
            model.addAttribute("eur", bankService.getEur());
        }
        return result;
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public String registration(Model model, @RequestParam String login, @RequestParam String password,
                               @RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam String passwordRepeat, @RequestParam String email,
                               @RequestParam String phone) {

        // check if login exists, passwords match and empty fields
        boolean loginExist = bankService.loginExists(login);
        boolean passwordsMatch = false;
        boolean noEmptyFields = true;
        String result = "";

        // check matching passwords
        if (password.equals(passwordRepeat) && !password.equals("")) {
            passwordsMatch = true;
        }
        // check empty fields
        if (login.equals("") || password.equals("") || passwordRepeat.equals("") ||
                firstName.equals("") || lastName.equals("")) {
            noEmptyFields = false;
        }
        // if login exists,
        if (loginExist) {
            result = "index";
            model.addAttribute("message", "Assigned login already exists. Please choose another login.");
        }
        // if login exists and passwords doesn't match
        if (!loginExist && !passwordsMatch) {
            model.addAttribute("message", "Passwords don't match. Please enter matching passwords.");
            result = "index";
        }
        // if login isn't exists and empty fields presents
        if (!loginExist && !noEmptyFields) {
            model.addAttribute("message", "You left empty fields. Please fill up obligatory fields.");
            result = "index";
        }

        if (!loginExist && passwordsMatch && noEmptyFields) {
            Customer customer = new Customer(login, password, firstName, lastName, email, phone);
            bankService.addCustomer(customer);
            eventDAO.createEvent(login, "You've signed up successfully.");
            model.addAttribute("message", "You've signed up successfully.");
            model.addAttribute("status", 1);
            model.addAttribute("login", login);
            model.addAttribute("accounts", bankService.findAccountsByLogin(login));
            model.addAttribute("totalAmount", bankService.totalAmount(login));
            model.addAttribute("deposits", bankService.findDepositsByLogin(login));
            model.addAttribute("credits", bankService.findCreditsByLogin(login));
            model.addAttribute("events", eventDAO.findEventsByLogin(login));
            model.addAttribute("usd", bankService.getUsd());
            model.addAttribute("eur", bankService.getEur());
            result = "main";
        }
        return result;
    }
}