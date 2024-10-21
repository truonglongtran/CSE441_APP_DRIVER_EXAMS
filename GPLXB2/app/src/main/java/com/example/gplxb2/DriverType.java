package com.example.gplxb2;

public class DriverType {
    private int id;
    private String title;
    private int quantity;
    private String description;

    public DriverType(int id, String title, int quantity, String description) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }
}




