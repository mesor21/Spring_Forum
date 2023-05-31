package com.example.demo.entity;

public class User {
    private Long id;
    private String login;
    private String incorporationDate;
    private String email;
    private int countMassage;

    public User() {
    }

    public User(Long id, String login, String incorporationDate, String email, int countMassage) {
        this.id = id;
        this.login = login;
        this.incorporationDate = incorporationDate;
        this.email = email;
        this.countMassage = countMassage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setIncorporationDate(String incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountMassage(int countMassage) {
        this.countMassage = countMassage;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getIncorporationDate() {
        return incorporationDate;
    }

    public String getEmail() {
        return email;
    }

    public int getCountMassage() {
        return countMassage;
    }
}
