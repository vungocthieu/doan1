package com.example.doan1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan1.R;
import com.example.doan1.ReadActivity;
import com.example.doan1.TruyenDetailActivity;
import com.example.doan1.models.SearchBook;

import java.util.List;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.SearchViewHolder> {

    private final List<SearchBook> bookList;
    private final Context context;

    public SearchBookAdapter(List<SearchBook> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_book, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchBook book = bookList.get(position);
        holder.txtTitle.setText(book.title);
        holder.txtAuthor.setText("Tác giả: " + book.author_name);

        Glide.with(context)
                .load(book.cover_image_url)
                .placeholder(R.drawable.image_load_error)
                .into(holder.imgCover);

        // 👇 Bắt sự kiện click để mở ReadActivity với book_id
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TruyenDetailActivity.class);
            intent.putExtra("book_id", book.book_id);
            intent.putExtra("title", book.title);
            intent.putExtra("tacGia", book.author_name);
            intent.putExtra("cover_image_url", book.cover_image_url);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView txtTitle, txtAuthor;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgCover);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
        }
    }
}
