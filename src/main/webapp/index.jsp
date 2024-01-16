<%--suppress HtmlFormInputWithoutLabel --%>
<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<form method="post" action="/login/login">
  <input  name="username" type="text" placeholder="username" required="">
  <input  name="password" type="password" placeholder="password"  required="">
  <button  name="Login">Login</button>
</form>
</body>
</html>