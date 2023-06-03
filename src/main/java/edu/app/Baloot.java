package edu.app;

import edu.app.model.*;
import edu.app.service.BalootService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@SpringBootApplication
@RestController
public class Baloot {
    public static void main(String[] args) throws Exception {

        BalootService.getInstance();
        SpringApplication.run(Baloot.class, args);
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Discount.class)
                .addAnnotatedClass(Provider.class)
                .addAnnotatedClass(Commodity.class)
                .addAnnotatedClass(Comment.class)
                .addAnnotatedClass(CommodityInBuyList.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            User user = BalootService.getInstance().getDatabase().getUsers().get(0);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Data is Added");

        } finally {
            // TODO: handle finally clause
            session.close();
            factory.close();
        }
    }

}
