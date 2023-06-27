package com.example.expendituremanagementapp.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expendituremanagementapp.database.DatabaseHelper;

import java.time.LocalDate;

public class Renevue {
    private int id = -1, renevueTypeId=-1, userId = -1;
    private String name, note;
    private float price;
    private String date;

    public Renevue() {}

    public Renevue(int id, String name, float price, String note, String date, int userId, int renevueTypeId) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.price = price;
        this.date = date;
        this.renevueTypeId = renevueTypeId;
        this.userId = userId;
    }
    public Renevue(String name, float price, String note, String date, int userId, int renevueTypeId) {
        this.name = name;
        this.note = note;
        this.price = price;
        this.date = date;
        this.renevueTypeId = renevueTypeId;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRenevueTypeId() {
        return renevueTypeId;
    }

    public void setRenevueTypeId(int renevueTypeId) {
        this.renevueTypeId = renevueTypeId;
    }

    public String getDate() {
        return date;
    }
}
