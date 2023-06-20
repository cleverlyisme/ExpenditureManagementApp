package com.example.expendituremanagementapp.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expendituremanagementapp.database.DatabaseHelper;

public class Renevue {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db = dbHelper.getWritableDatabase();
    private int id = -1, expenseTypeId = -1;
    private String name, note;
    private float price;

    public Renevue() {}

    public Renevue(int id, int expenseTypeId, String name, String note, float price) {
        this.id = id;
        this.expenseTypeId = expenseTypeId;
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

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
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

    public static LiveData<Float> totalRenevue() {
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
