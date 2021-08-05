<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TrustGame Login</title>
</head>
<body>
 <div align="center">
  <h1>Note: another browser is already logged in to the TrustGame using this userid.</h1>
  <h1>Log out there first or close the browser if you want to log in again.</h1>
  <br><br>
  <h1>TrustGame Login</h1>
  <form action="<%=request.getContextPath()%>/login" method="post">
   <table>
    <tr>
     <td>UserName</td>
     <td><input type="text" name="username" /></td>
    </tr>
    <tr>
     <td>Password</td>
     <td><input type="password" name="password" /></td>
    </tr>

   </table>
   <input type="submit" value="Submit" />
  </form>
 </div>
</body>
</html>