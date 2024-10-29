package com.example.gplxb2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.SignViewHolder> {
    private Context context;
    private List<Sign> signList;
    private int titleCount = 0; // Biến đếm để theo dõi số tiêu đề đã gặp

    public SignAdapter(Context context, List<Sign> signList) {
        this.context = context;
        this.signList = signList;
    }

    @NonNull
    @Override
    public SignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sign, parent, false);
        return new SignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignViewHolder holder, int position) {
        Sign sign = signList.get(position);

        // Kiểm tra nếu đây là tiêu đề (không có mô tả và ảnh)
        boolean isTitle = (sign.getDes() == null || sign.getDes().isEmpty()) &&
                (sign.getImagePath() == null || sign.getImagePath().equals("bienbao.png"));

        if (isTitle) {
            // Tăng biến đếm số tiêu đề lên
            titleCount++;

            // Cấu hình cho tiêu đề
            holder.nameTextView.setText(sign.getName());
            holder.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Kích thước chữ là 25
            holder.nameTextView.setTypeface(null, Typeface.BOLD); // Đặt chữ in đậm
            holder.nameTextView.setTextColor(Color.BLACK); // Màu chữ đen

            // Đặt màu nền dựa trên chỉ số tiêu đề
            int titleIndex = sign.getTitleIndex();
            switch (titleIndex) {
                case 0:
                    holder.itemView.setBackgroundColor(Color.RED); // Nền màu đỏ cho tiêu đề đầu tiên
                    break;
                case 1:
                    holder.itemView.setBackgroundColor(Color.YELLOW); // Nền màu vàng cho tiêu đề thứ hai
                    break;
                default:
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Nền trong suốt cho các tiêu đề khác
                    break;
            }

            // Căn giữa theo chiều dọc và chiều ngang
            holder.nameTextView.setGravity(Gravity.CENTER);

            // Đảm bảo ẩn mô tả và hình ảnh
            holder.descriptionTextView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        } else {
            // Cấu hình cho phần tử thông thường
            holder.nameTextView.setText(sign.getName());
            holder.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Kích thước chữ bình thường
            holder.nameTextView.setTypeface(null, Typeface.BOLD); // Đặt chữ in đậm

            // Hiển thị hoặc ẩn mô tả
            if (sign.getDes() == null || sign.getDes().isEmpty()) {
                holder.descriptionTextView.setVisibility(View.GONE);
            } else {
                holder.descriptionTextView.setText(sign.getDes());
                holder.descriptionTextView.setVisibility(View.VISIBLE);
            }

            // Hiển thị hoặc ẩn hình ảnh
            if (sign.getImagePath() != null && !sign.getImagePath().equals("bienbao.png")) {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Đặt nền trong suốt cho phần tử thông thường
                // Sử dụng Glide để tải hình ảnh
                Glide.with(context)
                        .load(sign.getImagePath())
                        .placeholder(R.drawable.bienbao) // Hình ảnh mặc định khi đang tải
                        .error(R.drawable.bienbao) // Hình ảnh khi có lỗi tải
                        .into(holder.imageView);
            } else {
                holder.imageView.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo Intent để chuyển đến SignDetailActivity
                Intent intent = new Intent(context, SignDetailActivity.class);
                intent.putExtra("name", sign.getName());
                intent.putExtra("description", sign.getDes());
                intent.putExtra("imagePath", sign.getImagePath());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return signList.size();
    }

    public static class SignViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        ImageView imageView;

        public SignViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_name);
            descriptionTextView = itemView.findViewById(R.id.text_description);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
