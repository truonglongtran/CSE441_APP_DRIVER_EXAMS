package com.example.gplxb2;

// Lớp đại diện cho các tùy chọn trả lời cho một câu hỏi
public class Options {
    private String A; // Tùy chọn A
    private String B; // Tùy chọn B
    private String C; // Tùy chọn C
    private String D; // Tùy chọn D
    private String E; // Tùy chọn E

    // Constructor để khởi tạo một đối tượng Options với các tùy chọn
    public Options(String A, String B, String C, String D, String E) {
        this.A = A; // Khởi tạo tùy chọn A
        this.B = B; // Khởi tạo tùy chọn B
        this.C = C; // Khởi tạo tùy chọn C
        this.D = D; // Khởi tạo tùy chọn D
        this.E = E; // Khởi tạo tùy chọn E
    }

    // Phương thức để lấy giá trị của tùy chọn A
    public String getA() {
        return A;
    }

    // Phương thức để lấy giá trị của tùy chọn B
    public String getB() {
        return B;
    }

    // Phương thức để lấy giá trị của tùy chọn C
    public String getC() {
        return C;
    }

    // Phương thức để lấy giá trị của tùy chọn D
    public String getD() {
        return D;
    }

    // Phương thức để lấy giá trị của tùy chọn E
    public String getE() {
        return E;
    }
}
