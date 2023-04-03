package edu.app.Controller;

import edu.app.Baloot;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = "/commodities/*")
public class CommodityController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] urlParameters = request.getPathInfo().split("/");
        if (urlParameters.length < 2) {
            response.sendRedirect("/Baloot/commodities");
        } else if (urlParameters.length > 2) {
            request.getRequestDispatcher("/JSP/404.jsp").forward(request, response);
        } else {

            try {
                long id = Long.parseLong(urlParameters[1]);
                Baloot baloot = Baloot.getInstance();
                if (baloot.getLoggedUser() == null)
                    throw new RuntimeException("Your not logged in");
                if(baloot.getDatabase().findCommodity(id) == null)
                    throw new RuntimeException("Commodity with id " + id + " not found");
                request.setAttribute("commodityId", id);
                request.getRequestDispatcher("/JSP/Commodity.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.getRequestDispatcher("/JSP/404.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error_message", e.getMessage());
                request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
            }

        }

    }

}
