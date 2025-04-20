<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="../../styles/number.css">
<form action="number" method="post">
  <input type="number" name="number" placeholder="введите число" required>
  <button type="submit">Выполнить</button>
  <% if (request.getAttribute("error") != null) { %>
  <p style="color: red;"><%= request.getAttribute("error") %></p>
  <% } %>
  <% if (request.getAttribute("result") != null) { %>
  <p style="color: green;">Результат: <%= request.getAttribute("result") %></p>
  <% } %>
</form>