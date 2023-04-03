package edu.app.Controller;

import edu.app.Baloot;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


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

        String username = request.getParameter("username");
        String password = request.getParameter("Password");
        try {
            Baloot baloot = Baloot.getInstance();
            baloot.login(username, password);
            response.sendRedirect("/Baloot/");
        } catch (Exception e) {
            System.out.println("error............: " + e.getMessage());
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }
    }
}
