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
  <title>Редактировать профиль</title>
  <style>
    .message { color: green; }
    .error { color: red; }
  </style>
</head>
<body>
<h2>Редактировать профиль</h2>

<% if (request.getAttribute("message") != null) { %>
<p class="message"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p class="error"><%= request.getAttribute("error") %></p>
<% } %>

<form action="editProfile" method="post">
  <label for="login">Логин:</label>
  <input type="text" id="login" name="login" value="<%= user.getLogin() %>" required><br><br>

  <label for="email">Email:</label>
  <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required><br><br>

  <label for="password">Новый пароль (оставьте пустым, если не хотите менять):</label>
  <input type="password" id="password" name="password"><br><br>

  <input type="submit" value="Сохранить изменения">
</form>

<p><a href="profile">Вернуться к профилю</a></p>
</body>
</html>