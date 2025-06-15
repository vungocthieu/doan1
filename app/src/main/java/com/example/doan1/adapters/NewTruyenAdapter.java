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
import com.example.doan1.TruyenDetailActivity;
import com.example.doan1.models.Truyen;

import java.util.List;

public class NewTruyenAdapter extends RecyclerView.Adapter<NewTruyenAdapter.ViewHolder> {

    private Context context;
    private List<Truyen> list;

    public NewTruyenAdapter(Context context, List<Truyen> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_truyen_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Truyen t = list.get(position);
        holder.tvTen.setText(t.getTen());
        holder.tvTacGia.setText(t.getTacgia());
        Glide.with(context).load(t.getHinhanh()).into(holder.imgTruyen);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TruyenDetailActivity.class);
            intent.putExtra("tenTruyen", t.getTen());
            intent.putExtra("tacGia", t.getTacgia());
            intent.putExtra("hinhAnh", t.getHinhanh());
            intent.putExtra("book_id", t.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTruyen;
        TextView tvTen, tvTacGia;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTruyen = itemView.findViewById(R.id.imgTruyen);
            tvTen = itemView.findViewById(R.id.tvTenTruyen); // id trong XML
            tvTacGia = itemView.findViewById(R.id.tvTacGia);  // sửa thành id đúng nếu bạn để là tvTacGia
        }
    }
}
