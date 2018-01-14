package MyApp.config;

import MyApp.model.Customer;
import MyApp.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreateDefaultCustomer {

    @Autowired
    private BankService bankService;

    @PostConstruct
    public void createDefaultUser() {
        Customer defaultCustomer = new Customer("user", "user", "user",
                "user", "user", "user");
        bankService.addCustomer(defaultCustomer);
    }
    }
