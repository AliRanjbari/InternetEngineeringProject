package edu.app.Controller;

import java.io.*;


import edu.app.Baloot;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/logout")
public class LogoutController extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Baloot baloot = null;
        try {
            baloot = Baloot.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        baloot.logout();
        response.sendRedirect("/Baloot/");
    }

}
