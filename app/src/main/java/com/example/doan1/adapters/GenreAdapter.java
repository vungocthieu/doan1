package com.example.doan1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.doan1.R;
import com.example.doan1.models.Genre;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    public interface OnGenreClickListener {
        void onGenreClick(Genre genre);
    }

    private List<Genre> genreList;
    private OnGenreClickListener listener;

    public GenreAdapter(List<Genre> genreList, OnGenreClickListener listener) {
        this.genreList = genreList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.tvGenreName.setText(genre.getGenre_name());
        holder.cardGenre.setOnClickListener(v -> listener.onGenreClick(genre));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenreName;
        CardView cardGenre;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenreName = itemView.findViewById(R.id.tvGenreName);
            cardGenre = itemView.findViewById(R.id.cardGenre);
        }
    }
}


