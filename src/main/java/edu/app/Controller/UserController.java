package edu.app.Controller;

import edu.app.api.Commodity;
import edu.app.api.Discount;
import edu.app.api.Provider;
import edu.app.api.User;
import edu.app.service.BalootService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public ResponseEntity getProviderById(){

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            User user = BalootService.getInstance().getLoggedUser();
            Map<String, Object> body = new HashMap<String, Object>();
            body.put("username" , user.getUserName());
            body.put("email" , user.getEmail());
            body.put("birthDay", user.getBirthDay());
            body.put("address" , user.getAddress());
            body.put("credit" , user.getCredit());
            body.put("currentDiscount" , user.getCurrentDiscount());
            body.put("usedDiscount" , user.getUsedDiscount());
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
