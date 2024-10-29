package com.example.gplxb2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DriverTypeAdapter extends RecyclerView.Adapter<DriverTypeAdapter.ViewHolder> {
    private Context context;
    private List<DriverType> driverTypeList;

    public DriverTypeAdapter(Context context, List<DriverType> driverTypeList) {
        this.context = context;
        this.driverTypeList = driverTypeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DriverType driverType = driverTypeList.get(position);
        holder.titleTextView.setText(driverType.getTitle());
        holder.descriptionTextView.setText(driverType.getDescription());

        // Hiển thị số lượng câu hỏi bên phải
        holder.quantityTextView.setText("Gồm: " + driverType.getQuantity() + " câu");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TestActivity.class);
            intent.putExtra("examsIndex", driverType.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return driverTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView, quantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
        }
    }
}

