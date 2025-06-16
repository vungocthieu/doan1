package com.example.doan1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.doan1.R;
import com.example.doan1.TruyenDetailActivity;
import com.example.doan1.models.Truyen;
import com.example.doan1.utils.UserSession;

import org.json.JSONObject;

import java.util.ArrayList;

public class TuTruyenAdapter extends RecyclerView.Adapter<TuTruyenAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Truyen> list;

    public TuTruyenAdapter(Context context, ArrayList<Truyen> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TuTruyenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tutruyen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TuTruyenAdapter.ViewHolder holder, int position) {
        Truyen truyen = list.get(position);

        holder.tvTitle.setText(truyen.getTenTruyen());
        holder.tvAuthor.setText(truyen.getTacGia());
        holder.tvGenre.setText(truyen.getTheLoai());
        holder.tvStatus.setText("Đã lưu");

        Glide.with(context)
                .load(truyen.getHinhAnh())
                .placeholder(R.drawable.dau_pha_thuong_khung)
                .into(holder.imgCover);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TruyenDetailActivity.class);
            intent.putExtra("book_id", truyen.getId());
            intent.putExtra("tenTruyen", truyen.getTenTruyen());
            intent.putExtra("tacGia", truyen.getTacGia());
            intent.putExtra("hinhAnh", truyen.getHinhAnh());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            int userId = UserSession.getUserId(context);
            String url = "http://192.168.1.153/api/delete_userbook.php";

            JSONObject body = new JSONObject();
            try {
                body.put("user_id", userId);
                body.put("book_id", truyen.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body,
                    response -> {
                        if (response.optBoolean("success")) {
                            list.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, list.size());
                            Toast.makeText(context, "Đã xóa khỏi tủ truyện", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        Toast.makeText(context, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                    });

            Volley.newRequestQueue(context).add(request);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView tvTitle, tvAuthor, tvGenre, tvStatus;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnDelete = itemView.findViewById(R.id.btnDelete); // đúng chỗ là ở đây
        }
    }
}
