package edu.app.Controller;

import edu.app.Baloot;
import edu.app.api.Commodity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/commodities")
public class CommoditiesController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Baloot baloot = Baloot.getInstance();
            if(baloot.getLoggedUser() == null)
                throw new RuntimeException("Your not logged in");
            request.setAttribute("commodities", baloot.getCommodities());
            request.getRequestDispatcher("/JSP/Commodities.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String actionType = request.getParameter("action");
        String searchField = request.getParameter("search");
        try {
            Baloot baloot = Baloot.getInstance();
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




















