package edu.app;

import edu.app.service.BalootService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class Baloot {


    public static void main(String[] args) throws Exception {

/*        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        try {
            BalootService.getInstance().importDataFromWeb();
            // Start scheduler for wait list
            new MinJob().run();
            scheduler.scheduleAtFixedRate(new MinJob(), 0, 1, TimeUnit.MINUTES);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*System.out.println("run mere");*/
        BalootService.getInstance();
        SpringApplication.run(Baloot.class, args);
    }
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

}
