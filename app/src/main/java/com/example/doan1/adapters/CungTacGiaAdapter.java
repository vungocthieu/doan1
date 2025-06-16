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
import com.example.doan1.models.Truyen;

import java.util.List;

public class CungTacGiaAdapter extends RecyclerView.Adapter<CungTacGiaAdapter.TruyenViewHolder> {

    private Context context;
    private List<Truyen> truyenList;

    public CungTacGiaAdapter(Context context, List<Truyen> truyenList) {
        this.context = context;
        this.truyenList = truyenList;
    }

    @NonNull
    @Override
    public TruyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_vertical, parent, false);
        return new TruyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenViewHolder holder, int position) {
        Truyen truyen = truyenList.get(position);
        holder.tvTen.setText(truyen.getTen());
        Glide.with(context).load(truyen.getHinhanh()).into(holder.imgTruyen);
    }

    @Override
    public int getItemCount() {
        return truyenList.size();
    }

    public static class TruyenViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTruyen;
        TextView tvTen;

        public TruyenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTruyen = itemView.findViewById(R.id.imgCover);
            tvTen = itemView.findViewById(R.id.tvTitle);
        }
    }
}
