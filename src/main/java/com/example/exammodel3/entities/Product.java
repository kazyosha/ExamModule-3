package com.example.exammodel3.entities;

public class Product {
    private int id;
    private String name;
    private double price;
    private String color;
    private int quantity;
    private String description;
    private Category category;

    public Product() {
    }

    public Product(int id, String name, double price, String color, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.description = description;
        this.category = category;
    }

    public Product(int id, String name, double price, int quantity, String color, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
