package edu.app.Controller;

import java.io.*;

import edu.app.Baloot;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/")
public class HomeController extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{


        Baloot baloot = null;
        try {
            baloot = Baloot.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if(baloot.getLoggedUser() != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/JSP/Home.jsp");
            requestDispatcher.forward(request, response);
        }
        else {
            response.sendRedirect("/Baloot/login");
        }


    }
}