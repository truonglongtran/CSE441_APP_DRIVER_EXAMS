package com.example.gplxb2;

// Class representing a traffic sign
public class Sign {
    private String name; // The name of the sign
    private String des; // The description of the sign
    private String imagePath; // The file path to the sign's image
    private int titleIndex; // The index indicating if this sign is a title (used for grouping)

    // Constructor for creating a Sign object with name, description, and image path
    public Sign(String name, String des, String imagePath) {
        this.name = name; // Initialize the name field
        this.des = des; // Initialize the description field
        this.imagePath = imagePath; // Initialize the image path field
        this.titleIndex = -1; // Default title index indicating it's not a title
    }

    // Constructor for creating a Sign object with name, description, image path, and title index
    public Sign(String name, String des, String imagePath, int titleIndex) {
        this.name = name; // Initialize the name field
        this.des = des; // Initialize the description field
        this.imagePath = imagePath; // Initialize the image path field
        this.titleIndex = titleIndex; // Initialize the title index field
    }

    // Getter method for the name field
    public String getName() {
        return name; // Return the name of the sign
    }

    // Getter method for the description field
    public String getDes() {
        return des; // Return the description of the sign
    }

    // Getter method for the image path field
    public String getImagePath() {
        // Return the image path if it is not null or empty, otherwise return a default image path
        return (imagePath != null && !imagePath.isEmpty()) ? imagePath : "bienbao.png";
    }

    // Getter method for the title index field
    public int getTitleIndex() {
        return titleIndex; // Return the title index of the sign
    }
}
