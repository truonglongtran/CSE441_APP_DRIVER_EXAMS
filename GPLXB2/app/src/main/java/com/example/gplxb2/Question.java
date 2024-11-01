package com.example.gplxb2;

// Lớp đại diện cho một câu hỏi trong ứng dụng
public class Question {
    private String id; // ID của câu hỏi
    private String questionText; // Nội dung câu hỏi
    private Options options; // Các tùy chọn trả lời
    private String answer; // Đáp án đúng
    private String suggest; // Gợi ý cho câu hỏi
    private Image image; // Hình ảnh liên quan đến câu hỏi

    // Constructor để khởi tạo một đối tượng Question
    public Question(String id, String questionText, Options options, String answer, String suggest, Image image) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
        this.suggest = suggest;
        this.image = image;
    }

    // Phương thức để lấy ID của câu hỏi
    public String getId() {
        return id;
    }

    // Phương thức để lấy nội dung câu hỏi
    public String getQuestionText() {
        return questionText;
    }

    // Phương thức để lấy các tùy chọn trả lời
    public Options getOptions() {
        return options;
    }

    // Phương thức để lấy đáp án đúng
    public String getAnswer() {
        return answer;
    }

    // Phương thức để lấy gợi ý cho câu hỏi
    public String getSuggest() {
        return suggest;
    }

    // Phương thức để lấy hình ảnh liên quan đến câu hỏi
    public Image getImage() {
        return image;
    }

    // Lớp lồng trong Question để đại diện cho hình ảnh
    public static class Image {
        private String img1; // URL của hình ảnh

        // Constructor để khởi tạo đối tượng Image
        public Image(String img1) {
            this.img1 = img1;
        }

        // Phương thức để lấy URL của hình ảnh
        public String getImg1() {
            return img1;
        }
    }
}
