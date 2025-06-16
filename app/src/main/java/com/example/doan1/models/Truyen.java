package com.example.doan1.models;

public class Truyen {
    private int id;
    private String tenTruyen;
    private String tacGia;
    private String hinhAnh;
    private String theLoai;

    public Truyen(int id, String tenTruyen, String tacGia, String hinhAnh, String theLoai) {
        this.id = id;
        this.tenTruyen = tenTruyen;
        this.tacGia = tacGia;
        this.hinhAnh = hinhAnh;
        this.theLoai = theLoai;
    }

    public int getId() {
        return id;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public String getTacGia() {
        return tacGia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }
    public String getTen() {
        return tenTruyen;
    }

    public String getHinhanh() {
        return hinhAnh;
    }

}
