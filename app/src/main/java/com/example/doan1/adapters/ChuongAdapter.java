package com.example.doan1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.R;
import com.example.doan1.ReadActivity;
import com.example.doan1.models.Chuong;

import java.util.List;

public class ChuongAdapter extends RecyclerView.Adapter<ChuongAdapter.ChuongViewHolder> {
    private Context context;
    private List<Chuong> chuongList;

    public ChuongAdapter(Context context, List<Chuong> chuongList) {
        this.context = context;
        this.chuongList = chuongList;
    }

    @NonNull
    @Override
    public ChuongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chuong, parent, false);
        return new ChuongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChuongViewHolder holder, int position) {
        Chuong chuong = chuongList.get(position);
        holder.tvTenChuong.setText(chuong.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReadActivity.class);
            intent.putExtra("chapter_id", chuong.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chuongList == null ? 0 : chuongList.size();
    }

    public static class ChuongViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenChuong;

        public ChuongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenChuong = itemView.findViewById(R.id.tvTenChuong);
        }
    }
}
