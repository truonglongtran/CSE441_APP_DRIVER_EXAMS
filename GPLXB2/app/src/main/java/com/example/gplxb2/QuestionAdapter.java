package com.example.gplxb2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Adapter class for displaying questions in a RecyclerView
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private final List<Question> questions; // List of questions to be displayed
    private final Map<String, String> selectedAnswers = new HashMap<>(); // Map to hold selected answers
    private final List<String> criticals; // List of critical questions

    // Constructor to initialize the adapter with questions and criticals
    public QuestionAdapter(List<Question> questions, List<String> criticals) {
        this.questions = questions;
        this.criticals = criticals;
    }

    // Inflates the layout for each question item
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(view); // Return a new ViewHolder
    }

    // Binds data to the ViewHolder for each question
    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position); // Get the current question

        // Log the question index and ID for debugging
        Log.d("QuestionAdapter", "Displaying question at position: " + position + ", Question ID: " + question.getId());

        // Set the question text in the TextView
        String title = "Câu " + (position + 1) + ": " + question.getQuestionText();
        holder.questionTextView.setText(title);

        // Load the question image if available using Glide
        String img1Url = question.getImage().getImg1();
        if (img1Url != null && !img1Url.isEmpty()) {
            holder.questionImageView.setVisibility(View.VISIBLE); // Show image view if URL is valid
            Glide.with(holder.itemView.getContext())
                    .load(img1Url)
                    .into(holder.questionImageView);
        } else {
            holder.questionImageView.setVisibility(View.GONE); // Hide image view if no image URL
        }

        // Set options visibility and text for each possible answer
        holder.optionAButton.setVisibility(View.GONE);
        holder.optionBButton.setVisibility(View.GONE);
        holder.optionCButton.setVisibility(View.GONE);
        holder.optionDButton.setVisibility(View.GONE);
        holder.optionEButton.setVisibility(View.GONE);

        // Set the text and visibility of each option based on question data
        if (!question.getOptions().getA().equals("null")) {
            holder.optionAButton.setText(question.getOptions().getA());
            holder.optionAButton.setVisibility(View.VISIBLE);
        }

        if (!question.getOptions().getB().equals("null")) {
            holder.optionBButton.setText(question.getOptions().getB());
            holder.optionBButton.setVisibility(View.VISIBLE);
        }

        if (!question.getOptions().getC().equals("null")) {
            holder.optionCButton.setText(question.getOptions().getC());
            holder.optionCButton.setVisibility(View.VISIBLE);
        }

        if (!question.getOptions().getD().equals("null")) {
            holder.optionDButton.setText(question.getOptions().getD());
            holder.optionDButton.setVisibility(View.VISIBLE);
        }

        if (!question.getOptions().getE().equals("null")) {
            holder.optionEButton.setText(question.getOptions().getE());
            holder.optionEButton.setVisibility(View.VISIBLE);
        }

        // Listener for when an answer is selected
        holder.answerGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = holder.itemView.findViewById(checkedId); // Get the selected RadioButton
            if (selectedButton != null) {
                String selectedAnswerText = selectedButton.getText().toString(); // Get the text of the selected answer
                String selectedKey = getSelectedKey(selectedAnswerText, holder); // Get the corresponding key

                // Store the selected answer in the map
                selectedAnswers.put(question.getId(), selectedKey);
                Log.d("QuestionAdapter", "Question ID: " + question.getId() + ", Selected answer key: " + selectedKey);
            }
        });

        holder.answerGroup.clearCheck(); // Clear any previously selected answer
    }

    // Helper method to determine the key for the selected answer
    private String getSelectedKey(String selectedAnswerText, QuestionViewHolder holder) {
        if (selectedAnswerText.equals(holder.optionAButton.getText().toString())) {
            return "a"; // Option A key
        } else if (selectedAnswerText.equals(holder.optionBButton.getText().toString())) {
            return "b"; // Option B key
        } else if (selectedAnswerText.equals(holder.optionCButton.getText().toString())) {
            return "c"; // Option C key
        } else if (selectedAnswerText.equals(holder.optionDButton.getText().toString())) {
            return "d"; // Option D key
        } else if (selectedAnswerText.equals(holder.optionEButton.getText().toString())) {
            return "e"; // Option E key
        }
        return ""; // Default return for no match
    }

    // Returns the total number of questions
    @Override
    public int getItemCount() {
        return questions.size();
    }

    // Returns the selected answers map
    public Map<String, String> getSelectedAnswers() {
        return selectedAnswers;
    }

    // ViewHolder class to hold the views for each question item
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView; // TextView for displaying the question
        ImageView questionImageView; // ImageView for displaying the question image
        RadioGroup answerGroup; // RadioGroup for holding answer options
        RadioButton optionAButton; // RadioButton for option A
        RadioButton optionBButton; // RadioButton for option B
        RadioButton optionCButton; // RadioButton for option C
        RadioButton optionDButton; // RadioButton for option D
        RadioButton optionEButton; // RadioButton for option E

        // Constructor for the ViewHolder
        public QuestionViewHolder(View itemView) {
            super(itemView);
            // Initialize views
            questionTextView = itemView.findViewById(R.id.question_text);
            questionImageView = itemView.findViewById(R.id.question_image);
            answerGroup = itemView.findViewById(R.id.answer_group);
            optionAButton = itemView.findViewById(R.id.option_a);
            optionBButton = itemView.findViewById(R.id.option_b);
            optionCButton = itemView.findViewById(R.id.option_c);
            optionDButton = itemView.findViewById(R.id.option_d);
            optionEButton = itemView.findViewById(R.id.option_e);
        }
    }
}
