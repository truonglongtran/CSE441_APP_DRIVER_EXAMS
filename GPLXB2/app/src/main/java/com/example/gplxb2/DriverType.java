package com.example.gplxb2;

public class DriverType {
    private int id; // Unique identifier for the driver type
    private String title; // Title of the driver type
    private int quantity; // Number of questions associated with this driver type
    private String description; // Description of the driver type

    // Constructor to initialize a DriverType object with provided values
    public DriverType(int id, String title, int quantity, String description) {
        this.id = id; // Set the unique identifier
        this.title = title; // Set the title
        this.quantity = quantity; // Set the quantity of questions
        this.description = description; // Set the description
    }

    // Getter method to retrieve the id
    public int getId() {
        return id;
    }

    // Getter method to retrieve the title
    public String getTitle() {
        return title;
    }

    // Getter method to retrieve the quantity
    public int getQuantity() {
        return quantity;
    }

    // Getter method to retrieve the description
    public String getDescription() {
        return description;
    }
}
