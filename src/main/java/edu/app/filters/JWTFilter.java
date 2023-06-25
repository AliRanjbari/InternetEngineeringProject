package edu.app.filters;

import edu.app.model.User.User;
import edu.app.model.User.UserDao;
import edu.app.security.JWTUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class JWTFilter implements Filter {

    @Autowired
    private UserDao userDao;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        System.out.println("jwt filter url " + url);
        String method = request.getMethod();

        if(url.equals("/register") || url.equals("/login"))
            chain.doFilter(request, response);
        else {
            String token = request.getHeader("Authorization");
            if(token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("You have not authorized yet!");
            }
            else {
                String username = JWTUtils.verifyJWT(token);
                if(username == null) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().println("The JWT token is invalidated!");
                }
                else {
                    try {
                        User user = userDao.findByUserName(username).get();
                        request.setAttribute("student", username);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chain.doFilter(request, response);
                }
            }
        }
    }
}
