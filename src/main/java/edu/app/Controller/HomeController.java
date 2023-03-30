package edu.app.Controller;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/")
public class HomeController extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/JSP/Home.jsp");

        requestDispatcher.forward(request, response);

    }
}
