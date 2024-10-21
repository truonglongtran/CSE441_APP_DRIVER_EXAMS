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

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private final List<Question> questions; // Danh sách câu hỏi
    private final Map<String, String> selectedAnswers = new HashMap<>(); // Lưu trữ câu trả lời đã chọn
    private final List<String> criticals;

    // Constructor
    public QuestionAdapter(List<Question> questions, List<String> criticals) {
        this.questions = questions;
        this.criticals = criticals; // Gán giá trị cho criticals
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);

        // Log thông tin câu hỏi và vị trí
        Log.d("QuestionAdapter", "Hiển thị câu hỏi tại vị trí: " + position + ", ID câu hỏi: " + question.getId());
        String title = "Câu " + (position + 1) + ": " + question.getQuestionText();
        holder.questionTextView.setText(title);

        // Load the image if it exists
        String img1Url = question.getImage().getImg1();
        if (img1Url != null && !img1Url.isEmpty()) {
            holder.questionImageView.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext())
                    .load(img1Url)
                    .into(holder.questionImageView);
        } else {
            holder.questionImageView.setVisibility(View.GONE);
        }

        // Set options visibility and text
        holder.optionAButton.setVisibility(View.GONE);
        holder.optionBButton.setVisibility(View.GONE);
        holder.optionCButton.setVisibility(View.GONE);
        holder.optionDButton.setVisibility(View.GONE);
        holder.optionEButton.setVisibility(View.GONE);

        // Set options dynamically
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

        // Lắng nghe sự thay đổi của RadioGroup
        holder.answerGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = holder.itemView.findViewById(checkedId);
            if (selectedButton != null) {
                String selectedAnswerText = selectedButton.getText().toString(); // Text của đáp án được chọn
                String selectedKey = getSelectedKey(selectedAnswerText, holder); // Lấy khóa đáp án được chọn

                // Lưu lựa chọn của người dùng
                selectedAnswers.put(question.getId(), selectedKey);

                // Log đáp án đã chọn
                Log.d("QuestionAdapter", "Câu hỏi ID: " + question.getId() + ", Khóa đáp án được chọn: " + selectedKey);
            }
        });

        // Xóa lựa chọn nếu chưa có lựa chọn
        holder.answerGroup.clearCheck();
    }

    private String getSelectedKey(String selectedAnswerText, QuestionViewHolder holder) {
        if (selectedAnswerText.equals(holder.optionAButton.getText().toString())) {
            return "a";
        } else if (selectedAnswerText.equals(holder.optionBButton.getText().toString())) {
            return "b";
        } else if (selectedAnswerText.equals(holder.optionCButton.getText().toString())) {
            return "c";
        } else if (selectedAnswerText.equals(holder.optionDButton.getText().toString())) {
            return "d";
        } else if (selectedAnswerText.equals(holder.optionEButton.getText().toString())) {
            return "e";
        }
        return ""; // Trả về chuỗi rỗng nếu không tìm thấy khóa
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    // Lấy các đáp án đã chọn
    public Map<String, String> getSelectedAnswers() {
        return selectedAnswers;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        ImageView questionImageView;
        RadioGroup answerGroup;
        RadioButton optionAButton;
        RadioButton optionBButton;
        RadioButton optionCButton;
        RadioButton optionDButton;
        RadioButton optionEButton;

        public QuestionViewHolder(View itemView) {
            super(itemView);
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
