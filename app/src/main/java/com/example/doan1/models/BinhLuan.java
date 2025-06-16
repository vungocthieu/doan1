package com.example.doan1.models;

public class BinhLuan {
    private String username;
    private String comment;
    private String date;

    public BinhLuan(String username, String comment, String date) {
        this.username = username;
        this.comment = comment;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }
}
