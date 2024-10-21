package com.example.gplxb2;

public class Question {
    private String id;
    private String questionText;
    private Options options;
    private String answer;
    private String suggest;
    private Image image;

    public Question(String id, String questionText, Options options, String answer, String suggest, Image image) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
        this.suggest = suggest;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Options getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSuggest() {
        return suggest;
    }

    public Image getImage() {
        return image;
    }

    // Lớp Image lồng trong lớp Question
    public static class Image {
        private String img1;

        public Image(String img1) {
            this.img1 = img1;
        }

        public String getImg1() {
            return img1;
        }
    }
}
