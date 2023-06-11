package edu.app.controller;

import edu.app.model.Comment.Comment;
import edu.app.model.Comment.CommentDao;
import edu.app.model.Commodity.CommodityDao;
import edu.app.model.Provider.ProviderDao;
import edu.app.service.BalootService;
import edu.app.model.Commodity.Commodity;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;


@RestController
@RequestMapping("/commodities")
public class CommoditiesController extends HttpServlet {

    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private ProviderDao providerDao;
    @Autowired
    private CommentDao commentDao;

    @GetMapping("")
    public ResponseEntity getCommodities (@RequestParam(value = "Sort" , defaultValue = "0" , required = false)
                                         int Sort, @RequestParam(value = "PageNum" , defaultValue = "1" ,
                                         required = false) int PageNum , @RequestParam(value = "Available" ,
                                         defaultValue = "false" ,required = false) boolean Available ,
                                         final HttpServletResponse response) throws  Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            Map<String , Object> body = new HashMap<>();
            List<Commodity> commodities = commodityDao.findAll(Available, Sort);
            List<Commodity> commoditiesPage;
            int numberOfPages;
            commoditiesPage = CommodityDao.paginate(PageNum , commodities);
            numberOfPages = (int) ceil((double)commodities.size()/12);
            body.put("total_page", (Object) numberOfPages);
            body.put("commodities" ,commoditiesPage);
            body.put("page_number" , (Object) PageNum);
            return ResponseEntity.status(HttpStatus.OK).body(body);

        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity getCommodityById(@PathVariable long id) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            Commodity commodity = commodityDao.findById(id).get();
            Map<String, Object> body = new HashMap<>();
            body.put("commodity" , commodity);
            body.put("comment" , commodity.getCommentList());
            body.put("suggestion", BalootService.getInstance().getMostSimilarCommodities(commodity));
            body.put("providerName", providerDao.findById(commodity.getProviderId()).get().getName());
            body.put("totalRates", commodity.getUserRates().size());
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getCommoditiesByName(@RequestParam(value = "Sort" , defaultValue = "0" , required = false)
                                               int Sort,@RequestParam(value = "PageNum" , defaultValue = "1" , required = false)
                                               int PageNum , @RequestParam(value = "Available" , defaultValue = "false",
                                               required = false) boolean Available ,final HttpServletResponse response,
                                               @PathVariable String name) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            Map<String , Object> body = new HashMap<>();
            List<Commodity> commodities = commodityDao.findByName(name, Available, Sort);
            List<Commodity> commoditiesPage;
            int numberOfPages;
            commoditiesPage = CommodityDao.paginate(PageNum , commodities);
            numberOfPages = (int) ceil((double)commodities.size()/12);
            body.put("total_page", (Object) numberOfPages);
            body.put("commodities" ,commoditiesPage);
            body.put("page_number" , (Object) PageNum);
            return ResponseEntity.status(HttpStatus.OK).body(body);

        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/category/{cat}")
    public ResponseEntity getCommoditiesByCategory(@RequestParam(value = "Sort" , defaultValue = "0" , required = false)
                                               int Sort,@RequestParam(value = "PageNum" , defaultValue = "1" , required = false)
                                               int PageNum , @RequestParam(value = "Available" , defaultValue = "false" ,
                                               required = false) boolean Available ,final HttpServletResponse response,
                                               @PathVariable String cat) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");
            Map<String , Object> body = new HashMap<>();
            List<Commodity> commodities = commodityDao.findByCategory(cat, Available, Sort);
            List<Commodity> commoditiesPage;
            List<Commodity> CommoditiesByCategory;
            int numberOfPages;
            CommoditiesByCategory = BalootService.getInstance().getCommoditiesByCategoryAndList(cat , commodities);
            commoditiesPage = CommodityDao.paginate(PageNum , CommoditiesByCategory);
            numberOfPages = (int) ceil((double)CommoditiesByCategory.size()/12);
            body.put("total_page", (Object) numberOfPages);
            body.put("commodities" ,commoditiesPage);
            body.put("page_number" , (Object) PageNum);
            return ResponseEntity.status(HttpStatus.OK).body(body);


        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/provider/{providerName}")
    public ResponseEntity getCommoditiesByProvider(@RequestParam(value = "Sort" , defaultValue = "0" , required = false)
                                                   int Sort,@RequestParam(value = "PageNum" , defaultValue = "1" , required = false)
                                                   int PageNum , @RequestParam(value = "Available" , defaultValue = "false" ,
                                                   required = false) boolean Available ,final HttpServletResponse response,
                                                   @PathVariable String providerName) throws Exception {

        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");

            long providerId = providerDao.getIdByName(providerName);
            System.out.println("name: " + providerName + " privider Id: " + providerId);

            Map<String , Object> body = new HashMap<>();
            List<Commodity> commodities = commodityDao.findByProviderId(providerId, Available, Sort);
            List<Commodity> commoditiesPage;
            int numberOfPages;
            commoditiesPage = CommodityDao.paginate(PageNum , commodities);
            numberOfPages = (int) ceil((double)commodities.size()/12);
            body.put("total_page", (Object) numberOfPages);
            body.put("commodities" ,commoditiesPage);
            body.put("page_number" , (Object) PageNum);
            return ResponseEntity.status(HttpStatus.OK).body(body);

        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    /*@PostMapping("")
    public Object addCommodity (@RequestBody Commodity commodity, final HttpServletResponse response) throws Exception {
        if (BalootService.getInstance().getDatabase().findCommodity(commodity.getId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Commodity already existed");
        }
        BalootService.getInstance().getDatabase().addCommodity(commodity);
        return commodity;
    }*/

    @PostMapping("/{commodityId}")
    public ResponseEntity rateAndCommentCommodity (@RequestBody JSONObject Body, @PathVariable long commodityId) throws Exception {
        try {
            if (BalootService.getInstance().getLoggedUser() == null)
                throw new RuntimeException("You're not logged in");

            if (Body.get("comment") != null) {
                String comment = (String) Body.get("comment");
                Comment newComment = new Comment(BalootService.getInstance().getLoggedUser().getUserName(),  comment , LocalDate.of(2023,10,10));
                commentDao.save(newComment);
                // BalootService.getInstance().getDatabase().addComment(BalootService.getInstance().getLoggedUser().getEmail(), commodityId, comment , LocalDate.of(2023,10,10) );
            }
            if (Body.get("rate") != null) {
                double rate = (double) Body.get("rate");
                commodityDao.rateCommodity(BalootService.getInstance().getLoggedUser().getUserName() , commodityId,rate);
                //BalootService.getInstance().getDatabase().rateCommodity(BalootService.getInstance().getLoggedUser().getUserName() , commodityId,rate);
            }
            return ResponseEntity.ok("ok");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}