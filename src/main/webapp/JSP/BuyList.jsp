<%@ page import="edu.app.Baloot" %>
<%@ page import="edu.app.api.User" %>
<%@ page import="edu.app.api.Commodity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>


<%
    Baloot baloot = Baloot.getInstance();
    User user = baloot.getLoggedUser();
    NumberFormat nf = NumberFormat.getInstance();
%>

<html lang="en"><head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
        	padding: 5px
        }
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
    <a href="/Baloot">Home</a>
    <ul>
        <li id="username">Username: <%= user.getUserName() %></li>
        <li id="email">Email: <%= user.getEmail() %></li>
        <li id="birthDate">Birth Date: <%= user.getBirthDay() %></li>
        <li id="address"><%= user.getAddress() %></li>
        <li id="credit">Credit: <%= nf.format(user.getCredit()) %></li>
        <% if (user.hasDiscount()) {%>
            <li>Current Buy List Price: <%= nf.format(user.getTotalBuyListPrice()) %>
            [Discount "<%=user.getCurrentDiscount().getDiscountCode()%>" <%=user.getCurrentDiscount().getDiscount()%>%]</li>
        <%} else {%>
            <li>Current Buy List Price: <%= nf.format(user.getTotalBuyListPrice()) %></li>
        <%}%>
        <li>
            <a href="/Baloot/credit">Add Credit</a>
        </li>
        <li>
            <form action="" method="POST">
                <label>Submit & Pay</label>
                <input id="form_payment" type="hidden" name="userId" value="Farhad">
                <button type="submit" name="action" value="pay">Payment</button>
            </form>
        </li>

    <li>
    <form action="" method="POST">
        <label>Discount</label>
        <input type="text" name="code" value="Discount code">
        <button type="submit" name="action" value="discount">Set</button>
        <button type="submit" name="action" value="delete_discount">Delete Discount</button>
    </form>
    </li>
    </ul>
    <table>
        <caption>
            <h2>Buy List</h2>
        </caption>
        <tbody><tr>
            <th>Id</th> 
            <th>Name</th> 
            <th>Provider Name</th> 
            <th>Price</th> 
            <th>Categories</th> 
            <th>Rating</th> 
            <th>In Stock</th>
            <th></th>
            <th></th>
        </tr>
        <% for (Commodity commodity : user.getBuyList()) { %>
        <tr>
            <td><%=commodity.getId()%></td>
            <td><%=commodity.getName()%></td>
            <td><%=baloot.getProviderNameById(commodity.getProviderId())%></td>
            <td><%=nf.format(commodity.getPrice())%></td>
            <td><%=commodity.getCategories()%></td>
            <td><%=commodity.getRating()%></td>
            <td><%=commodity.getInStock()%></td>
            <td><a href="/Baloot/commodities/<%=commodity.getId()%>">Link</a></td>
            <td>        
                <form action="" method="POST">
                    <input id="form_commodity_id" type="hidden" name="commodityId" value="<%=commodity.getId()%>">
                    <button type="submit" name="action" value="delete">Remove</button>
                </form>
            </td>
        </tr>
        <% } %>
    </tbody></table>
</body></html>