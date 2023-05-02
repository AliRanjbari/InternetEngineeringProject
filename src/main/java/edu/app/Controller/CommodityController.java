package edu.app.Controller;

import edu.app.service.BalootService;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
                BalootService baloot = BalootService.getInstance();
                if (baloot.getLoggedUser() == null)
                    throw new RuntimeException("Your not logged in");
                if (baloot.getDatabase().findCommodity(id) == null)
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



    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] urlParameters = request.getPathInfo().split("/");
        if (urlParameters.length < 2) {
            response.sendRedirect("/Baloot/commodities");
        } else if (urlParameters.length > 2) {
            request.getRequestDispatcher("/JSP/404.jsp").forward(request, response);
        } else {
            try {
                long id = Long.parseLong(urlParameters[1]);
                BalootService baloot = BalootService.getInstance();
                if (baloot.getLoggedUser() == null)
                    throw new RuntimeException("Your not logged in");
                if(baloot.getDatabase().findCommodity(id) == null)
                    throw new RuntimeException("Commodity with id " + id + " not found");

                String actionType = request.getParameter("action");
                switch (actionType) {
                    case "like": {
                        handleLike(request, baloot);
                        break;
                    }
                    case "dislike": {
                        handleDislike(request, baloot);
                        break;
                    }
                    case "add": {
                        handleAddToBuyList(baloot, id);
                        break;
                    }
                    case "comment": {
                        handleComment(request, baloot, id);
                        break;
                    }
                    case "rate": {
                        handleRate(request, baloot, id);
                        break;
                    }
                    default: {
                        throw new RuntimeException("Action not found!");
                    }
                }

                response.sendRedirect("/Baloot/commodities/" + id);
            } catch (NumberFormatException e) {
                request.getRequestDispatcher("/JSP/404.jsp").forward(request, response);
            } catch (Exception e) {

                request.setAttribute("error_message", e.getMessage());
                request.getRequestDispatcher("/JSP/error.jsp").forward(request, response);
            }
        }
    }


    private void handleLike(HttpServletRequest request, BalootService baloot) throws Exception{
        if (request.getParameter("comment_id").isBlank())
            throw new RuntimeException("comment id can't be blank");
        int commentId = Integer.parseInt(request.getParameter("comment_id"));
        baloot.getDatabase().voteComment(baloot.getLoggedUser().getUserName(), commentId, 1);
    }

    private void handleDislike(HttpServletRequest request, BalootService baloot) throws Exception{
        if (request.getParameter("comment_id").isBlank())
            throw new RuntimeException("comment id can't be blank");
        int commentId = Integer.parseInt(request.getParameter("comment_id"));
        baloot.getDatabase().voteComment(baloot.getLoggedUser().getUserName(), commentId, -1);
    }

    private void handleComment(HttpServletRequest request, BalootService baloot, long commodityId) throws Exception {
        if (request.getParameter("comment").isBlank())
            throw new RuntimeException("comment can't be blank");
        String comment = request.getParameter("comment");
        baloot.getDatabase().addComment(baloot.getLoggedUser().getEmail(), commodityId, comment, LocalDate.now());
    }

    private void handleRate(HttpServletRequest request, BalootService baloot, long commodityId) throws Exception {
        if (request.getParameter("quantity").isBlank())
            throw new RuntimeException("quantity can't be blank");
        double score = Double.parseDouble(request.getParameter("quantity"));
        baloot.getDatabase().rateCommodity(baloot.getLoggedUser().getUserName(), commodityId, score);
    }

    private void handleAddToBuyList(BalootService baloot, long commodityId) throws Exception {
        baloot.getDatabase().addToBuyList(baloot.getLoggedUser().getUserName(), commodityId);
    }

}
