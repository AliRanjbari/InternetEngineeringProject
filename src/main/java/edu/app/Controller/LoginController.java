package edu.app.Controller;

import java.io.*;

import edu.app.Baloot;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
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
