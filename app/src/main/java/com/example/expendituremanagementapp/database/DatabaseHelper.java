package com.example.expendituremanagementapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expendituremanagementapp.model.Renevue;
import com.example.expendituremanagementapp.model.RenevueType;

import java.time.LocalDate;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "budget_management.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the necessary tables
        String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT)";
        db.execSQL(createUsersTableQuery);

        String createExpenseTypesTableQuery = "CREATE TABLE IF NOT EXISTS expense_types (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "userId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id))";
        db.execSQL(createExpenseTypesTableQuery);

        String createRevenueTypesTableQuery = "CREATE TABLE IF NOT EXISTS revenue_types (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "userId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id))";
        db.execSQL(createRevenueTypesTableQuery);

        String createExpensesTableQuery = "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "note TEXT, " +
                "userId INTEGER NOT NULL, " +
                "expenseTypeId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,"+
                "FOREIGN KEY (expenseTypeId) REFERENCES expenses(id) ON DELETE CASCADE)";
        db.execSQL(createExpensesTableQuery);

        String createRevenuesTableQuery = "CREATE TABLE IF NOT EXISTS revenues (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "note TEXT, " +
                "date DATE DEFAULT CURRENT_DATE, " +
                "userId INTEGER NOT NULL, " +
                "renevueTypeId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,"+
                "FOREIGN KEY (renevueTypeId) REFERENCES renevues(id) ON DELETE CASCADE)";
        db.execSQL(createRevenuesTableQuery);
    }

    public Cursor select(String table, int userID){
        String select = "SELECT * FROM "+table+" WHERE userId = "+userID+"";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(select, null);
    }
    public void insert_Renevue_Type(String name, int userID){
        String insert = "INSERT INTO revenue_types VALUES (null, '"+name+"', "+userID+")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insert);
    }
    public void update_Renevue_Type(RenevueType renevueType){
        String update = "UPDATE revenue_types SET name='"+renevueType.getName()+"' WHERE id='"+renevueType.getId()+"'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(update);
    }
    public void delete_Renevue(String table, int id){
        String delete = "DELETE FROM "+table+" WHERE id = "+id+"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(delete);
    }
    public void insert_Renevue(Renevue renevue){
        String insert = "INSERT INTO revenues VALUES(null, '"+renevue.getName()+"', "+renevue.getPrice()+", '"+renevue.getNote()+"', '"+renevue.getDate()+"', "+renevue.getUserId()+", "+renevue.getRenevueTypeId()+")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insert);
    }
    public void update_Renevue(Renevue renevue){
        String update = "UPDATE revenues SET name = '"+renevue.getName()+"', price = "+renevue.getPrice()+", note = '"+renevue.getNote()+"', date = '"+LocalDate.parse(renevue.getDate())+"' ";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(update);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Perform any necessary upgrades or migrations
        if (oldVersion < 2) {
            // Upgrade database from version 1 to version 2
        }
        onCreate(db);
    }
}
