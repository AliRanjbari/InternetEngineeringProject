package edu.app.controller;

import java.io.*;

import edu.app.service.BalootService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/JSP/Login.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("Password");
        try {
            BalootService baloot = BalootService.getInstance();
            baloot.login(username, password);
            response.sendRedirect("/Baloot/");
        } catch (Exception e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
        }
    }
}
