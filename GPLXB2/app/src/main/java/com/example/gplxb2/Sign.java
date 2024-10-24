package com.example.gplxb2;

public class Sign {
    private String name;
    private String des;
    private String imagePath;
    private int titleIndex; // Thêm thuộc tính titleIndex

    // Constructor cho các loại đối tượng khác nhau
    public Sign(String name, String des, String imagePath) {
        this.name = name;
        this.des = des;
        this.imagePath = imagePath;
        this.titleIndex = -1; // Giá trị mặc định nếu không phải là tiêu đề
    }

    // Constructor cho tiêu đề
    public Sign(String name, String des, String imagePath, int titleIndex) {
        this.name = name;
        this.des = des;
        this.imagePath = imagePath;
        this.titleIndex = titleIndex; // Gán chỉ số tiêu đề
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getImagePath() {
        return (imagePath != null && !imagePath.isEmpty()) ? imagePath : "bienbao.png"; // Hình ảnh mặc định
    }

    public int getTitleIndex() {
        return titleIndex; // Thêm phương thức getter cho titleIndex
    }
}
