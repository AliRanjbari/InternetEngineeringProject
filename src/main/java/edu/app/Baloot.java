package edu.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Baloot {
    public static void main(String[] args) throws Exception {

        SpringApplication.run(Baloot.class, args);
    }

}
