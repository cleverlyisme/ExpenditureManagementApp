package com.example.expendituremanagementapp.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expendituremanagementapp.database.DatabaseHelper;

import java.sql.Date;

public class Renevue {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db = dbHelper.getWritableDatabase();

    private int id = -1, renevueTypeId=-1, userId = -1;
    private String name, note;
    private float price;
    private Date date;

    public Renevue() {}

    public Renevue(int id, String name, String note, float price, int renevueTypeId, int userId) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.price = price;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static LiveData<Float> getTotalRenevue() {
        MutableLiveData<Float> totalRevenueLiveData = new MutableLiveData<>();

        Cursor cursor = db.rawQuery("SELECT SUM(price) FROM revenues", null);
        float sum = 0;

        if (cursor.moveToFirst()) {
            sum = cursor.getFloat(0);
        }

        cursor.close();
        db.close();

        totalRevenueLiveData.setValue(sum);

        return totalRevenueLiveData;
    }
}
