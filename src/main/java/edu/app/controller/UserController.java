package edu.app.controller;

import edu.app.model.Commodity.Commodity;
import edu.app.model.Commodity.CommodityDao;
import edu.app.model.CommodityInBuyList.CommodityInBuyList;
import edu.app.model.Discount.Discount;
import edu.app.model.Discount.DiscountDao;
import edu.app.model.User.User;
import edu.app.model.User.UserDao;
import edu.app.service.Utils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private DiscountDao discountDao;

    @GetMapping("")
    public ResponseEntity getUserInfo(@RequestAttribute("username") String username){
        try {
            User user = userDao.findByUserName(username).get();
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

    @PostMapping("/buyList")
    public ResponseEntity Purchase(@RequestAttribute("username") String username){

        try {
            User user = userDao.findByUserName(username).get();
            user.purchaseBuyList();
            userDao.save(user);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/buyList/discount")
    public ResponseEntity ApplyDiscount(@RequestBody JSONObject jsonData, @RequestAttribute("username") String username){
        try {
            String discountCode = (String) jsonData.get("discount");
            if(discountDao.findByDiscountCode(discountCode).isEmpty())
                throw new RuntimeException("Can't find this discount code");

            User user = userDao.findByUserName(username).get();
            Discount discount  = discountDao.findByDiscountCode(discountCode).get();
            user.setDiscount(discount);
            userDao.save(user);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/buyList")
    public ResponseEntity getUserBuyList(@RequestAttribute("username") String username){

        try {
            User user = userDao.findByUserName(username).get();
            Map<String, Object> body = new HashMap<>();
            List<CommodityInBuyList> commoditiesWithNumber = new ArrayList<>();
            List<CommodityInBuyList> newcommoditiesWithNumber = new ArrayList<>();
            for (int i = 0; i < user.getBuyList().size() ;i++) {
                CommodityInBuyList temp = new CommodityInBuyList(user.getBuyList().get(i) , user.getNumberOfCommodityInBuyList(user.getBuyList().get(i).getId()));
                commoditiesWithNumber.add(temp);
            }
            newcommoditiesWithNumber= Utils.removeDuplicate(commoditiesWithNumber);
            body.put("buyList" , newcommoditiesWithNumber);
            body.put("totalPriceWithDiscount", user.getTotalBuyListPrice());
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    @GetMapping("/historyList")
    public ResponseEntity getUserHistoryList(@RequestAttribute("username") String username){
        try {
            User user = userDao.findByUserName(username).get();
            Map<String, Object> body = new HashMap<String, Object>();
            body.put("historyList" , Utils.removeDuplicate(user.getPurchasedList()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/buyList/{commodityId}")
    public ResponseEntity addCommodityToBuyList (@PathVariable long commodityId, @RequestAttribute("username") String username) throws Exception {

        try {
            if (commodityDao.findById(commodityId).isEmpty())
                throw new RuntimeException("Can't find commodity");
            Commodity commodity = commodityDao.findById(commodityId).get();
            if (commodity.getInStock() == 0)
                throw new RuntimeException("There is not enough of this Commodity");

            User user = userDao.findByUserName(username).get();
            user.addItemToList(commodity);
            commodity.decreaseInStock();
            commodityDao.save(commodity);
            userDao.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(user.getBuyList());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/buyList/{commodityId}")
    public ResponseEntity RemoveCommodityFromBuyList (@PathVariable long commodityId, @RequestAttribute("username") String username) throws Exception {

        try {
            if (commodityDao.findById(commodityId).isEmpty())
                throw new RuntimeException("Can't find commodity");
            Commodity commodity = commodityDao.findById(commodityId).get();
            User user = userDao.findByUserName(username).get();
            user.removeCommodity(commodityId);
            commodity.increaseInStock();
            userDao.save(user);
            commodityDao.save(commodity);

            return ResponseEntity.status(HttpStatus.OK).body(user.getBuyList());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("addCredit/{amount}")
    public ResponseEntity addCredit (@PathVariable int amount, @RequestAttribute("username") String username) throws Exception {

        try {
            User user = userDao.findByUserName(username).get();
            user.addCredit(amount);
            userDao.addCredit(user.getId(), amount);
            return ResponseEntity.status(HttpStatus.OK).body(user.getCredit());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
