package com.example.gplxb2;

public class Sign {
    private String name;
    private String des;
    private String imagePath;
    private int titleIndex;

    public Sign(String name, String des, String imagePath) {
        this.name = name;
        this.des = des;
        this.imagePath = imagePath;
        this.titleIndex = -1;
    }

    public Sign(String name, String des, String imagePath, int titleIndex) {
        this.name = name;
        this.des = des;
        this.imagePath = imagePath;
        this.titleIndex = titleIndex;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getImagePath() {
        return (imagePath != null && !imagePath.isEmpty()) ? imagePath : "bienbao.png";
    }

    public int getTitleIndex() {
        return titleIndex;
    }
}
