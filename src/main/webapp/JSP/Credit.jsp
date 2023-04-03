<%@ page import="edu.app.Baloot" %>
<%@ page import="edu.app.api.User" %>

<%
  Baloot baloot = Baloot.getInstance();
  User user = baloot.getLoggedUser();
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Credit</title>
</head>

<body>
  <a href="/Baloot">Home</a>
  <p id="username">username: <%=user.getUserName()%></p>
  <p id="credit">Credit: <%=user.getCredit()%></p>
  <form method="post" action="">
    <label>Credits:</label>
    <input name="credit" type="number" value="0" step="1"/>
    <br>
    <button type="submit">Add credits</button>
  </form>
</body>
</html>