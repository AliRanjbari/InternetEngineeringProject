<%@ page import="edu.app.Baloot" %>
<%@ page import="edu.app.api.User" %>
<%@ page import="edu.app.api.Commodity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>


<%
    Baloot baloot = Baloot.getInstance();
    User user = baloot.getLoggedUser();
    List<Commodity> commodities = (List)request.getAttribute("commodities");
    NumberFormat nf = NumberFormat.getInstance();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
    <style>
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
    <a href="/Baloot">Home</a>
    <p id="username">username: <%=user.getUserName()%></p>
    <br><br>
    <form action="" method="POST">
        <label>Search:</label>
        <input type="text" name="search" value="">
        <button type="submit" name="action" value="search_by_category">Search By Cagtegory</button>
        <button type="submit" name="action" value="search_by_name">Search By Name</button>
        <button type="submit" name="action" value="clear">Clear Search</button>
    </form>
    <br><br>
    <form action="" method="POST">
        <label>Sort By:</label>
        <button type="submit" name="action" value="sort_by_price">price</button>
    </form>
    <br><br>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th> 
            <th>Provider Name</th>
            <th>Price</th>
            <th>Categories</th>
            <th>Rating</th>
            <th>In Stock</th>
            <th>Links</th>
        </tr>
        <% for (Commodity commodity : commodities ) { %>
        <tr>
            <td><%=commodity.getId()%></td>
            <td><%=commodity.getName()%></td>
            <td><%=baloot.getProviderNameById(commodity.getProviderId())%></td>
            <td><%=nf.format(commodity.getPrice())%></td>
            <td><%=commodity.getCategories()%></td>
            <td><%=commodity.getRating()%></td>
            <td><%=commodity.getInStock()%></td>
            <td><a href="/Baloot/commodities/<%=commodity.getId()%>">Link</a></td>
        </tr>
        <% } %>
    </table>
</body>
</html>