package edu.app.controller;

import edu.app.model.User.User;
import edu.app.model.User.UserDao;
import edu.app.service.BalootService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AuthenticationController {


    @Autowired
    private UserDao userDao;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody JSONObject loginData) throws IOException {
        try {
            User newUser = new User((String) loginData.get("username"),
                                    (String) loginData.get("password"),
                                    (String) loginData.get("email"),
                                    LocalDate.parse((String) loginData.get("birthDate")),
                                    (String) loginData.get("address"),
                                    0);
            this.userDao.save(newUser);
            return ResponseEntity.ok("ok");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid register");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody JSONObject loginData) throws IOException {
        try {
            User user = userDao.findByUserName((String) loginData.get("username")).get();
            BalootService.getInstance().login(user ,(String) loginData.get("password"));
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping ("/logout")
    public ResponseEntity logout() {
        try {
            BalootService.getInstance().logout();
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not found. invalid login");
        }
    }

}
