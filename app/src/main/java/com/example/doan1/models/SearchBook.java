package com.example.doan1.models;

public class SearchBook {
    public int book_id;
    public String title;
    public String cover_image_url;
    public String author_name;

    public SearchBook(int book_id, String title, String cover_image_url, String author_name) {
        this.book_id = book_id;
        this.title = title;
        this.cover_image_url = cover_image_url;
        this.author_name = author_name;
    }
}
