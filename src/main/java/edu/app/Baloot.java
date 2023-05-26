package edu.app;

import edu.app.service.BalootService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class Baloot {
    public static void main(String[] args) throws Exception {

        BalootService.getInstance();
        SpringApplication.run(Baloot.class, args);
    }

}
