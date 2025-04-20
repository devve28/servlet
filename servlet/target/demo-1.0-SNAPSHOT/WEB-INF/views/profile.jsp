<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.demo.model.User" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<html>
<head>
  <title>Профиль пользователя</title>
  <style>
    .message { color: green; }
    .error { color: red; }
  </style>
</head>
<body>
<h2>Профиль пользователя</h2>
<p>Добро пожаловать, <%= user.getLogin() %>!</p>
<p>Email: <%= user.getEmail() %></p>
<p>Статус: <%= user.getStatus() %></p>

<p><a href="${pageContext.request.contextPath}/editProfile">Редактировать профиль</a></p>
<p><a href="logout">Выйти</a></p>
</body>
</html>