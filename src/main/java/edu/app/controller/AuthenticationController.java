package edu.app.controller;

import edu.app.service.BalootService;
import java.io.IOException;
import java.time.LocalDate;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class AuthenticationController {


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody JSONObject loginData) throws IOException {
        try {
            BalootService.getInstance().Register((String) loginData.get("username"),
                                                 (String) loginData.get("password"),
                                                 (String) loginData.get("email"),
                            LocalDate.parse((String) loginData.get("birthDate")),
                                            (String) loginData.get("address"));
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
            BalootService.getInstance().login((String) loginData.get("username") ,(String) loginData.get("password"));
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not found. invalid login");
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
