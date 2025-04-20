<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="../../styles/signup.css">
        <form action="signup" method="post">
            <h2>Регистрация</h2>
            <input type="email" name="email" placeholder="Еmail" required>
            <input type="text" name="login" placeholder="Логин" required>
            <input type="password" name="password" placeholder="Пароль" required>
            <button type="submit">Зарегистрироваться</button>
            <% if (request.getAttribute("error") != null) { %>
            <p style="color: red;"><%= request.getAttribute("error") %></p>
            <% } %>
        </form>


