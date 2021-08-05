<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@page import="org.transsonic.trustgame.login.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TrustGame</title>
</head>
<body>
  <div align="center">
    <h1>Multiple valid games found</h1>
    <p>
      <% out.println(UserLoginServlet.selectGameTable(request)); %>
    </p>
  </div>
</body>
</html>