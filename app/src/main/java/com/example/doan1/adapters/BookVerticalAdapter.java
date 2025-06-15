package com.example.doan1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan1.R;
import com.example.doan1.models.Book;

import java.util.List;

public class BookVerticalAdapter extends RecyclerView.Adapter<BookVerticalAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;

    public BookVerticalAdapter(Context context, List<Book> books) {
        this.context = context;
        this.bookList = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_vertical, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.txtTitle.setText(book.title);
        holder.txtViews.setText(book.current_readers + " Đang đọc");
        holder.txtAuthor.setText("Tác giả #" + book.author_id); // Chưa join được tên

        Glide.with(context).load(book.cover_image_url).into(holder.imgCover);
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView txtTitle, txtAuthor, txtViews;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgCover);
            txtTitle = itemView.findViewById(R.id.tvTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtViews = itemView.findViewById(R.id.txtViews);
        }
    }
}
