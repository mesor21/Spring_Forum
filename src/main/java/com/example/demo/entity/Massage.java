package com.example.demo.entity;

public class Massage {
    private Long id;
    private Long userId;
    private String massage;

    public Massage() {
    }

    public Massage(Long id, Long userId, String massage) {
        this.id = id;
        this.userId = userId;
        this.massage = massage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMassage() {
        return massage;
    }
}
