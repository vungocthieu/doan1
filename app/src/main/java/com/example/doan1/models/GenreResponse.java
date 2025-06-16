package com.example.doan1.models;

import java.util.List;

public class GenreResponse {
    private boolean success;
    private List<Genre> genres;

    public boolean isSuccess() {
        return success;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}

