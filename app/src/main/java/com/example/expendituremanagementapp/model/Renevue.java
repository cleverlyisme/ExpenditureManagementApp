package com.example.expendituremanagementapp.model;

public class Renevue {
    private int id = -1, userId = -1;
    private String name, note;
    private float price;

    public Renevue() {}

    public Renevue(int id, int userId, String name, String note, float price) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.note = note;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
