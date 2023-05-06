package edu.app.Controller;

import edu.app.api.Commodity;
import edu.app.api.Discount;
import edu.app.api.Provider;
import edu.app.api.User;
import edu.app.service.BalootService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public ResponseEntity getUserInfo(){

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

    @GetMapping("/buyList")
    public ResponseEntity getUserBuyList(){

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            User user = BalootService.getInstance().getLoggedUser();
            Map<String, Object> body = new HashMap<String, Object>();
            Map<Long , Integer> numberOfCommoditiesInBuyList ;
            body.put("buyList" , user.getBuyList());
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    @GetMapping("/historyList")
    public ResponseEntity getUserHistoryList(){

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            User user = BalootService.getInstance().getLoggedUser();
            Map<String, Object> body = new HashMap<String, Object>();
            body.put("historyList" , user.getPurchasedList());
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/buylist/add/{commodityId}")
    public ResponseEntity addCommodityToBuyList (@PathVariable long commodityId) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            String LogedUserName = BalootService.getInstance().getLoggedUser().getUserName();
            BalootService.getInstance().getDatabase().addToBuyList(LogedUserName , commodityId);
            return ResponseEntity.status(HttpStatus.OK).body("Item added successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("addCredit/{amount}")
    public ResponseEntity addCredit (@PathVariable int amount) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            BalootService.getInstance().getLoggedUser().addCredit(amount);
            return ResponseEntity.status(HttpStatus.OK).body(BalootService.getInstance().getLoggedUser().getCredit());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
