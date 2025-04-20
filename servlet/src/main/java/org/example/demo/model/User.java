package org.example.demo.model;




public class User {
    private int id;
    private String login;
    private String password;
    private String email;
    private String status;
    private String confirmToken;

    public User(int id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.status = status;
        this.confirmToken = confirmToken;
    }

    // Конструктор для создания новых пользователей, до занесения в БД
    public User(String login, String password, String email, String confirmToken) {
        this.login = login;
        this.password = password;
        this.email = email;
        setStatus("PENDING"); // Устанавливаем статус по умолчанию "PENDING"
        this.confirmToken = confirmToken;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // Добавлены методы setter для всех полей, чтобы можно было обновлять данные пользователя.
    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }
}
