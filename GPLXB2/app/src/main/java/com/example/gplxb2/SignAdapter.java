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

// Adapter class for displaying sign data in a RecyclerView
public class SignAdapter extends RecyclerView.Adapter<SignAdapter.SignViewHolder> {
    private Context context; // Context to access application resources
    private List<Sign> signList; // List of signs to be displayed
    private int titleCount = 0; // Counter for title items

    // Constructor for the adapter, takes context and sign list as parameters
    public SignAdapter(Context context, List<Sign> signList) {
        this.context = context;
        this.signList = signList;
    }

    @NonNull
    @Override
    public SignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each sign
        View view = LayoutInflater.from(context).inflate(R.layout.item_sign, parent, false);
        return new SignViewHolder(view); // Return the created ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull SignViewHolder holder, int position) {
        Sign sign = signList.get(position); // Get the sign object for the current position
        // Determine if the current sign is a title (has no description and a specific image path)
        boolean isTitle = (sign.getDes() == null || sign.getDes().isEmpty()) &&
                (sign.getImagePath() == null || sign.getImagePath().equals("bienbao.png"));

        if (isTitle) {
            titleCount++; // Increment title count for styling or logic purposes
            holder.nameTextView.setText(sign.getName()); // Set the title name
            holder.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Set title text size
            holder.nameTextView.setTypeface(null, Typeface.BOLD); // Set title text style to bold
            holder.nameTextView.setTextColor(Color.BLACK); // Set title text color

            // Set the background color based on the title index
            int titleIndex = sign.getTitleIndex();
            switch (titleIndex) {
                case 0:
                    holder.itemView.setBackgroundColor(Color.RED); // Red background for title index 0
                    break;
                case 1:
                    holder.itemView.setBackgroundColor(Color.YELLOW); // Yellow background for title index 1
                    break;
                default:
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Default transparent background
                    break;
            }

            holder.nameTextView.setGravity(Gravity.CENTER); // Center the title text

            holder.descriptionTextView.setVisibility(View.GONE); // Hide description for title items
            holder.imageView.setVisibility(View.GONE); // Hide image for title items
        } else {
            // Configure regular sign item
            holder.nameTextView.setText(sign.getName()); // Set the sign name
            holder.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Set text size for regular items
            holder.nameTextView.setTypeface(null, Typeface.BOLD); // Set text style to bold

            // Handle visibility of description text
            if (sign.getDes() == null || sign.getDes().isEmpty()) {
                holder.descriptionTextView.setVisibility(View.GONE); // Hide if no description
            } else {
                holder.descriptionTextView.setText(sign.getDes()); // Set description text
                holder.descriptionTextView.setVisibility(View.VISIBLE); // Show description
            }

            // Handle visibility of image based on image path
            if (sign.getImagePath() != null && !sign.getImagePath().equals("bienbao.png")) {
                holder.imageView.setVisibility(View.VISIBLE); // Show image if valid path
                holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Set transparent background for regular items
                // Load the image using Glide
                Glide.with(context)
                        .load(sign.getImagePath()) // Load image from the given path
                        .placeholder(R.drawable.bienbao) // Placeholder image while loading
                        .error(R.drawable.bienbao) // Error image if loading fails
                        .into(holder.imageView); // Set the loaded image into the ImageView
            } else {
                holder.imageView.setVisibility(View.GONE); // Hide image if path is invalid
            }
        }

        // Set an onClickListener for each item to open detail activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignDetailActivity.class); // Create intent to navigate to detail activity
                intent.putExtra("name", sign.getName()); // Pass sign name to the detail activity
                intent.putExtra("description", sign.getDes()); // Pass sign description
                intent.putExtra("imagePath", sign.getImagePath()); // Pass image path
                context.startActivity(intent); // Start the detail activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return signList.size(); // Return the total number of items in the sign list
    }

    // ViewHolder class to hold the views for each sign item
    public static class SignViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView; // TextView for sign name
        TextView descriptionTextView; // TextView for sign description
        ImageView imageView; // ImageView for sign image

        // Constructor for the ViewHolder, initializes the views
        public SignViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_name); // Find sign name TextView
            descriptionTextView = itemView.findViewById(R.id.text_description); // Find sign description TextView
            imageView = itemView.findViewById(R.id.image_view); // Find sign image ImageView
        }
    }
}
