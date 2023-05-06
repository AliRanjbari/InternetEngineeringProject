package edu.app.Controller;

import edu.app.api.*;
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
            body.put("CartSize", user.getBuyList().size());
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
            Map<String, List<CommodityInBuyList>> body = new HashMap<>();
            List<CommodityInBuyList> commoditiesWithNumber = new ArrayList<>();
            List<CommodityInBuyList> newcommoditiesWithNumber = new ArrayList<>();
            for (int i = 0; i < user.getBuyList().size() ;i++) {
                CommodityInBuyList temp = new CommodityInBuyList(user.getBuyList().get(i) , user.getNumberOfCommodityInBuyList(user.getBuyList().get(i).getId()));
                commoditiesWithNumber.add(temp);
            }
            newcommoditiesWithNumber= BalootService.getInstance().removeDuplicate(commoditiesWithNumber);
            body.put("buyList" , commoditiesWithNumber);
            return ResponseEntity.status(HttpStatus.OK).body(newcommoditiesWithNumber);
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

    @PostMapping("/buyList/{commodityId}")
    public ResponseEntity addCommodityToBuyList (@PathVariable long commodityId) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            String LoggedUserName = BalootService.getInstance().getLoggedUser().getUserName();
            BalootService.getInstance().getDatabase().addToBuyList(LoggedUserName , commodityId);
            return ResponseEntity.status(HttpStatus.OK).body(BalootService.getInstance().getLoggedUser().getBuyList());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/buyList/{commodityId}")
    public ResponseEntity RemoveCommodityFromBuyList (@PathVariable long commodityId) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            String LoggedUserName = BalootService.getInstance().getLoggedUser().getUserName();
            BalootService.getInstance().getDatabase().removeFromBuyList(LoggedUserName, commodityId);
            return ResponseEntity.status(HttpStatus.OK).body(BalootService.getInstance().getLoggedUser().getBuyList());
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
