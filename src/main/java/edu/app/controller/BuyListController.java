package edu.app.controller;

import edu.app.service.BalootService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/buyList")
public class BuyListController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BalootService baloot = BalootService.getInstance();
            if (baloot.getLoggedUser() == null)
                throw new RuntimeException("Your not logged in");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/JSP/BuyList.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BalootService baloot = BalootService.getInstance();
            if (baloot.getLoggedUser() == null)
                throw new RuntimeException("Your not logged in");
            String actionType = request.getParameter("action");
            switch (actionType) {
                case "pay": {
                    baloot.getDatabase().purchaseBuyList(baloot.getLoggedUser().getUserName());
                    break;
                }
                case "delete": {
                    handleDelete(request, baloot);
                    break;
                }
                case "discount": {
                    handleDiscount(request, baloot);
                    break;
                }
                case "delete_discount": {
                    baloot.getLoggedUser().removeDiscount();
                    break;
                }
                default: {
                    throw new RuntimeException("Action not found!");
                }
            }
            request.getRequestDispatcher("/JSP/BuyList.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }
    }

    private void handleDelete(HttpServletRequest request, BalootService baloot) throws Exception{
        if (request.getParameter("commodityId").isBlank())
            throw new RuntimeException("commodityId id can't be blank");
        long commodityId = Long.parseLong(request.getParameter("commodityId"));
        baloot.getDatabase().removeFromBuyList(baloot.getLoggedUser().getUserName(), commodityId);
    }

    private void handleDiscount(HttpServletRequest request, BalootService baloot) throws Exception{
        if (request.getParameter("code").isBlank())
            throw new RuntimeException("Discount code can't be blank");
        String discountCode = request.getParameter("code");
        baloot.getDatabase().useDiscount(baloot.getLoggedUser(), discountCode);
    }

}
