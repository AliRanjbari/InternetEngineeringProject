package edu.app.controller;

import edu.app.model.User.User;
import edu.app.model.User.UserDao;
import edu.app.security.JWTUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
            String jwtToken = JWTUtils.createJWT(newUser.getUserName());
            return ResponseEntity.status(200).body(jwtToken);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid register");
        }
    }

    @GetMapping("/auth/github")
    public ResponseEntity authWithGithub(@RequestParam(value = "code" , required = true) String code) throws IOException, ParseException {
        String accessTokenUrl = "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s";
        String userInfoUrl = "https://api.github.com/user";
        String client_id = "a5ff31de5d6c7aee6b39";
        String client_secret = "10406702ea42b52c523e8a35e95d8533e1c6e71b";
        try{

            // 1. Getting access token
            Document doc = Jsoup.connect(String.format(accessTokenUrl, client_id, client_secret, code))
                    .ignoreContentType(true)
                    .header("Accept", "application/xml")
                    .execute()
                    .parse();

            if(doc.select("access_token").isEmpty())
                throw new RuntimeException(Objects.requireNonNull(doc.selectFirst("error")).text());
            String accessToken = Objects.requireNonNull(doc.selectFirst("access_token")).text();


            // 2. Getting user info
            String jsonString = Jsoup.connect(userInfoUrl)
                    .ignoreContentType(true)
                    .header("Authorization", String.format("token %s", accessToken))
                    .method(Connection.Method.GET)
                    .execute()
                    .body();
            Object o = new JSONParser().parse(jsonString);
            JSONObject obj = (JSONObject) o;
            String userName = (String) obj.get("login");
            String password = null;
            String email = (String) obj.get("email");
            LocalDate birthDay = LocalDate.now().minusYears(18);        // 18 years ago
            String address = "";

            // 3. Create of update user
            if (userDao.findByUserName(userName).isEmpty()) {
                User user = new User(userName, password, email, birthDay, address, 0);
                userDao.save(user);
            } else {
                User user = userDao.findByUserName(userName).get();
                user.update(password, email, birthDay, address);
                userDao.save(user);
            }

            // 4. generate jwtToken
            String jwtToken = JWTUtils.createJWT(userName);
            return ResponseEntity.status(200).body(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody JSONObject loginData) throws IOException {
        try {
            User user = userDao.findByUserName((String) loginData.get("username")).get();
            String jwtToken = JWTUtils.createJWT(user.getUserName());
            return ResponseEntity.status(200).body(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping ("/logout")
    public ResponseEntity logout() {
        try {
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not found. invalid login");
        }
    }

}
