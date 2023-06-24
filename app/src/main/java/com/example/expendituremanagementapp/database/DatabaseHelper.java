package com.example.expendituremanagementapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "budget_management.db";
    private static final int DATABASE_VERSION = 2;

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Perform any necessary upgrades or migrations
        if (oldVersion < 2) {
            // Upgrade database from version 1 to version 2
        }
    }
}
