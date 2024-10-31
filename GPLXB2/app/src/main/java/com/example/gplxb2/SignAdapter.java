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
    private int titleCount = 0;

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
        boolean isTitle = (sign.getDes() == null || sign.getDes().isEmpty()) &&
                (sign.getImagePath() == null || sign.getImagePath().equals("bienbao.png"));

        if (isTitle) {
            titleCount++;
            holder.nameTextView.setText(sign.getName());
            holder.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            holder.nameTextView.setTypeface(null, Typeface.BOLD);
            holder.nameTextView.setTextColor(Color.BLACK);

            int titleIndex = sign.getTitleIndex();
            switch (titleIndex) {
                case 0:
                    holder.itemView.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    break;
            }

            holder.nameTextView.setGravity(Gravity.CENTER);

            holder.descriptionTextView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        } else {
            // Cấu hình cho phần tử thông thường
            holder.nameTextView.setText(sign.getName());
            holder.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.nameTextView.setTypeface(null, Typeface.BOLD);

            if (sign.getDes() == null || sign.getDes().isEmpty()) {
                holder.descriptionTextView.setVisibility(View.GONE);
            } else {
                holder.descriptionTextView.setText(sign.getDes());
                holder.descriptionTextView.setVisibility(View.VISIBLE);
            }

            if (sign.getImagePath() != null && !sign.getImagePath().equals("bienbao.png")) {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                Glide.with(context)
                        .load(sign.getImagePath())
                        .placeholder(R.drawable.bienbao)
                        .error(R.drawable.bienbao)
                        .into(holder.imageView);
            } else {
                holder.imageView.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
