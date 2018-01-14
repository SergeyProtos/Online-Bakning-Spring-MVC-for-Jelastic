package MyApp.config;

import MyApp.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SetCourses {

    @Autowired
    private BankService bankService;

    @PostConstruct
    public void setCourses() {
        bankService.setCourses();
    }
}
