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
    private Context context; // Context to start new activities
    private List<DriverType> driverTypeList; // List of driver types to be displayed

    // Constructor to initialize context and driverTypeList
    public DriverTypeAdapter(Context context, List<DriverType> driverTypeList) {
        this.context = context;
        this.driverTypeList = driverTypeList;
    }

    // Method to create new ViewHolder instances
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_driver_type layout and create a new ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver_type, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DriverType driverType = driverTypeList.get(position); // Get the driver type at the current position
        holder.titleTextView.setText(driverType.getTitle()); // Set the title of the driver type
        holder.descriptionTextView.setText(driverType.getDescription()); // Set the description of the driver type

        // Set the quantity of questions for the driver type
        holder.quantityTextView.setText("Gồm: " + driverType.getQuantity() + " câu");

        // Set an OnClickListener on the itemView to handle clicks
        holder.itemView.setOnClickListener(v -> {
            // Create an Intent to navigate to TestActivity
            Intent intent = new Intent(context, TestActivity.class);
            intent.putExtra("examsIndex", driverType.getId()); // Pass the driver type ID to TestActivity
            context.startActivity(intent); // Start the TestActivity
        });
    }

    // Method to get the total number of items in the list
    @Override
    public int getItemCount() {
        return driverTypeList.size(); // Return the size of the driver type list
    }

    // ViewHolder class to hold the views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView, quantityTextView; // TextViews for title, description, and quantity

        // Constructor for the ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView); // Initialize title TextView
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView); // Initialize description TextView
            quantityTextView = itemView.findViewById(R.id.quantityTextView); // Initialize quantity TextView
        }
    }
}
