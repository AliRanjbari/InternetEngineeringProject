package edu.app.Controller;

import edu.app.service.BalootService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/credit")
public class CreditController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BalootService baloot = BalootService.getInstance();
            if(baloot.getLoggedUser() == null)
                throw new RuntimeException("Your not logged in");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/JSP/Credit.jsp");
            requestDispatcher.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long credit;
        try {
            credit = Long.parseLong(request.getParameter("credit"));

        } catch (Exception e) {
            request.setAttribute("error_message", "The credit value should be Integer Number");
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
            return;
        }

        try {
            if (credit < 0)
                throw new RuntimeException("Credit can't be negative number!");

            BalootService baloot = BalootService.getInstance();
            if(baloot.getLoggedUser() == null)
                throw new RuntimeException("Your not logged in");
            baloot.getLoggedUser().addCredit(credit);
            response.sendRedirect("/Baloot/credit");
        } catch (Exception e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }

    }
}
