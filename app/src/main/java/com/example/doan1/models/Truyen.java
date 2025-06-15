package com.example.doan1.models;

public class Truyen {
    private int id;
    private String ten;
    private String tacgia;
    private String hinhanh;
    private String gioithieu;

    public Truyen(int id, String ten, String tacgia, String hinhanh, String gioithieu) {
        this.id = id;
        this.ten = ten;
        this.tacgia = tacgia;
        this.hinhanh = hinhanh;
        this.gioithieu = gioithieu;
    }

    public Truyen(String ten, String tacgia, String hinhanh) {
        this.ten = ten;
        this.tacgia = tacgia;
        this.hinhanh = hinhanh;
    }

    public String getTen() {
        return ten;
    }

    public String getTacgia() {
        return tacgia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }
}
