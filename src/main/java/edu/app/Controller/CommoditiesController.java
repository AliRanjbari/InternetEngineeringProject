package edu.app.Controller;

import edu.app.service.BalootService;
import edu.app.api.Commodity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.min;


@RestController
@RequestMapping("/commodities")
public class CommoditiesController extends HttpServlet {

    @GetMapping("")
    public List<Commodity> getcommodities (@RequestParam(value = "PageNum" , defaultValue = "1" , required = false) int PageNum ,
                                           final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<Commodity> commodities = BalootService.getInstance().getCommodities();
            List<Commodity> temp = commodities.subList((PageNum - 1) * 12 ,  min(((PageNum)* 12 ) , commodities.size()));
            return temp;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }

    @PostMapping("")
    public Object addCommodity (@RequestBody Commodity commodity, final HttpServletResponse response) throws Exception {
        if (BalootService.getInstance().getCommoditiesByName(commodity.getName()) != null) {
            Exception e = null;
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
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




















