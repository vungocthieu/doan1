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

    private final Context context;
    private final List<Book> bookList;
    private final OnBookClickListener listener;

    // Giao diện lắng nghe click
    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    // Constructor
    public BookVerticalAdapter(Context context, List<Book> books, OnBookClickListener listener) {
        this.context = context;
        this.bookList = books;
        this.listener = listener;
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

        // Set dữ liệu
        holder.txtTitle.setText(book.title);
        holder.txtViews.setText(book.current_readers + " đang đọc");

        // Nếu bạn có book.author_name thì dùng, không có thì dùng author_id
        String authorText = (book.author_name != null && !book.author_name.isEmpty())
                ? book.author_name : "Tác giả #" + book.author_id;
        holder.txtAuthor.setText(authorText);

        // Load ảnh bằng Glide
        Glide.with(context)
                .load(book.cover_image_url)
                .placeholder(R.drawable.image_load_error)
                .into(holder.imgCover);

        // Gán sự kiện click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    // ViewHolder
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView txtTitle, txtAuthor, txtViews;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgBook);
            txtTitle = itemView.findViewById(R.id.tvTenBook);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtViews = itemView.findViewById(R.id.txtViews);
        }
    }
}
