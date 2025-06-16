package com.example.doan1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.R;
import com.example.doan1.models.BinhLuan;

import java.util.List;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHolder> {

    private Context context;
    private List<BinhLuan> binhLuanList;

    public BinhLuanAdapter(Context context, List<BinhLuan> binhLuanList) {
        this.context = context;
        this.binhLuanList = binhLuanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_binh_luan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BinhLuan binhLuan = binhLuanList.get(position);
        holder.tvUsername.setText(binhLuan.getUsername());
        holder.tvComment.setText(binhLuan.getComment());
        holder.tvDate.setText(binhLuan.getDate());
    }

    @Override
    public int getItemCount() {
        return binhLuanList == null ? 0 : binhLuanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvComment, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
