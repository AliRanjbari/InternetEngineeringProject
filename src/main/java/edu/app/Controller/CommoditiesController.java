package edu.app.Controller;

import edu.app.service.BalootService;
import edu.app.api.Commodity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;


@RestController
@RequestMapping("/commodities")
public class CommoditiesController extends HttpServlet {
    @GetMapping("")
    public ResponseEntity getCommodities (@RequestParam(value = "PageNum" , defaultValue = "1" , required = false)
                                         int PageNum , @RequestParam(value = "Available" , defaultValue = "false" ,
                                         required = false) boolean Available ,final HttpServletResponse response)
                                        throws  IOException {

        try {
            Map<String , Object> body = new HashMap<>();
            List<Commodity> commodities;
            List<Commodity> commoditiesPage;
            int numberOfPages;

            if(Available) {
                commodities = BalootService.getInstance().getDatabase().getAvailableCommodities();
            }
            else {
                commodities = BalootService.getInstance().getCommodities();
            }
            commoditiesPage = BalootService.getInstance().getDatabase().getPage(PageNum , commodities);
            numberOfPages = (int) ceil((double)commodities.size()/12);
            body.put("total_page", (Object) numberOfPages);
            body.put("commodities" ,commoditiesPage);
            body.put("page_number" , (Object) PageNum);
            return ResponseEntity.status(HttpStatus.OK).body(body);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity getCommodityById(@PathVariable long id){

        try {
            Commodity commodity = BalootService.getInstance().getDatabase().findCommodity(id);
            Map<String, Object> body = new HashMap<>();
            body.put("commodity" , commodity);
            body.put("comment" , commodity.getCommentList());
            body.put("suggestion", BalootService.getInstance().getMostSimilarCommodities(commodity));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getCommoditiesByName(@RequestParam(value = "PageNum" , defaultValue = "1" , required = false)
                                               int PageNum , @RequestParam(value = "Available" , defaultValue = "false",
                                               required = false) boolean Available ,final HttpServletResponse response,
                                               @PathVariable String name) throws IOException {
        try {
            List<Commodity> commodities;
            List<Commodity> commoditiesPage;
            List<Commodity> CommoditiesByName;
            if(Available) {
                commodities = BalootService.getInstance().getDatabase().getAvailableCommodities();
            }
            else {
                commodities = BalootService.getInstance().getCommodities();
            }
            CommoditiesByName = BalootService.getInstance().getCommoditiesByNameAndList(name , commodities);
            commoditiesPage = BalootService.getInstance().getDatabase().getPage(PageNum , CommoditiesByName);
            return ResponseEntity.status(HttpStatus.OK).body(commoditiesPage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }
    @GetMapping("/category/{cat}")
    public ResponseEntity getCommoditiesByCategory(@RequestParam(value = "PageNum" , defaultValue = "1" , required = false)
                                               int PageNum , @RequestParam(value = "Available" , defaultValue = "false" ,
            required = false) boolean Available ,final HttpServletResponse response,
                                               @PathVariable String cat)
            throws IOException {
        try {
            List<Commodity> commodities;
            List<Commodity> commoditiesPage;
            List<Commodity> CommoditiesByCategory;
            if(Available) {
                commodities = BalootService.getInstance().getDatabase().getAvailableCommodities();
            }
            else {
                commodities = BalootService.getInstance().getCommodities();
            }
            CommoditiesByCategory = BalootService.getInstance().getCommoditiesByCategoryAndList(cat , commodities);
            commoditiesPage = BalootService.getInstance().getDatabase().getPage(PageNum , CommoditiesByCategory);
            return ResponseEntity.status(HttpStatus.OK).body(commoditiesPage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }
    @GetMapping("/provider/{providerName}")
    public ResponseEntity getCommoditiesByProvider(@RequestParam(value = "PageNum" , defaultValue = "1" , required = false)
                                                   int PageNum , @RequestParam(value = "Available" , defaultValue = "false" ,
            required = false) boolean Available ,final HttpServletResponse response,
                                                   @PathVariable String providerName)
            throws IOException {
        try {
            List<Commodity> commodities;
            List<Commodity> commoditiesPage;
            List<Commodity> CommoditiesByProvider;
            if(Available) {
                commodities = BalootService.getInstance().getDatabase().getAvailableCommodities();
            }
            else {
                commodities = BalootService.getInstance().getCommodities();
            }
            CommoditiesByProvider = BalootService.getInstance().getCommoditiesByProviderAndList(providerName , commodities);
            commoditiesPage = BalootService.getInstance().getDatabase().getPage(PageNum , CommoditiesByProvider);
            return ResponseEntity.status(HttpStatus.OK).body(commoditiesPage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @PostMapping("")
    public Object addCommodity (@RequestBody Commodity commodity, final HttpServletResponse response) throws Exception {
        if (BalootService.getInstance().getDatabase().findCommodity(commodity.getId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Commodity already existed");
        }
        else
        BalootService.getInstance().getDatabase().addCommodity(commodity);
        return commodity;
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String actionType = request.getParameter("action");
        String searchField = request.getParameter("search");
        try {
            BalootService baloot = BalootService.getInstance();
            List<Commodity> commodities = null;
            if(baloot.getLoggedUser() == null)
                throw new RuntimeException("Your not logged in");
            switch (actionType) {
                case "search_by_category":
                    commodities = baloot.getCommoditiesByCategory(searchField);
                    break;
                case "search_by_name":
                    commodities = baloot.getCommoditiesByName(searchField);
                    break;
                case "clear":
                    commodities = baloot.getCommodities();
                    break;
                case "sort_by_price":
                    commodities = baloot.getCommoditiesSortByPrice();
                    break;
            }
            request.setAttribute("commodities", commodities);
            request.getRequestDispatcher("/JSP/Commodities.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }

    }
}




















