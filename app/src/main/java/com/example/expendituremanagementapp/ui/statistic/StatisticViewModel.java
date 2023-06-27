package com.example.expendituremanagementapp.ui.statistic;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.helper.AsyncTaskExecutorService;
import com.example.expendituremanagementapp.model.ExpenseTypeStatistic;
import com.example.expendituremanagementapp.model.RenevueTypeStatistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticViewModel extends AndroidViewModel {
    private DatabaseHelper db;
    private MutableLiveData<Float> totalRevenue, totalExpense;
    private MutableLiveData<List<RenevueTypeStatistic>> lsr;
    private MutableLiveData<List<ExpenseTypeStatistic>> lst;

    public StatisticViewModel(Application application) {
        super(application);

        db = new DatabaseHelper(application);
        totalRevenue = new MutableLiveData<>();
        totalExpense = new MutableLiveData<>();
        lsr = new MutableLiveData<>();
        lst = new MutableLiveData<>();
    }

    public LiveData<List<RenevueTypeStatistic>> getListRenevueTypeStatistic() {
        new ListRenevueTypeStatisticAsyncTask().execute();
        return lsr;
    }

    public LiveData<List<ExpenseTypeStatistic>> getListExpenseTypeStatistic() {
        new ListExpenseTypeStatisticAsyncTask().execute();
        return lst;
    }

    public LiveData<Float> getTotalRenevue() {
        new TotalRenevueAsyncTask().execute();
        return totalRevenue;
    }

    public LiveData<Float> getTotalExpense() {
        new TotalExpenseAsyncTask().execute();
        return totalExpense;
    }

    private class ListRenevueTypeStatisticAsyncTask extends AsyncTaskExecutorService<Void, Void, List<RenevueTypeStatistic>> {
        protected List<RenevueTypeStatistic> doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT r.renevueTypeId, rt.name, " +
                    "sum(r.price) AS total FROM renevues r" +
                    "INNER JOIN renevue_types rt ON r.renevueTypeId = rt.id " +
                    "GROUP BY  r.renevueTypeId, rt.name", null);
            List<RenevueTypeStatistic> ls = new ArrayList<>();
            while (cursor.moveToNext()) {
                ls.add(new RenevueTypeStatistic(cursor.getInt(0),
                        cursor.getString(1), cursor.getFloat(2)));
            }
            return ls;
        }

        protected void onPostExecute(List<RenevueTypeStatistic> ls) {
            lsr.setValue(ls);
        }
    }

    private class ListExpenseTypeStatisticAsyncTask extends AsyncTaskExecutorService<Void, Void, List<ExpenseTypeStatistic>> {
        protected List<ExpenseTypeStatistic> doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT r.expenseTypeId, rt.name, " +
                    "sum(r.price) AS total FROM expenses r" +
                    "INNER JOIN expense_types rt ON r.renevueTypeId = rt.id " +
                    "GROUP BY  r.expenseTypeId, rt.name", null);
            List<ExpenseTypeStatistic> ls = new ArrayList<>();
            while (cursor.moveToNext()) {
                ls.add(new ExpenseTypeStatistic(cursor.getInt(0),
                        cursor.getString(1), cursor.getFloat(2)));
            }
            return ls;
        }

        protected void onPostExecute(List<ExpenseTypeStatistic> ls) {
            lst.setValue(ls);
        }
    }

    private class TotalRenevueAsyncTask extends AsyncTaskExecutorService<Void, Void, Float> {
        protected Float doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT SUM(price) FROM revenues", null);
            float sum = 0;
            if (cursor.moveToFirst()) {
                sum = cursor.getFloat(0);
            }
            return sum;
        }

        protected void onPostExecute(Float sum) {
            totalRevenue.setValue(sum);
        }
    }

    private class TotalExpenseAsyncTask extends AsyncTaskExecutorService<Void, Void, Float> {
        protected Float doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT SUM(price) FROM expenses", null);
            float sum = 0;
            if (cursor.moveToFirst()) {
                sum = cursor.getFloat(0);
            }
            return sum;
        }

        protected void onPostExecute(Float sum) {
            totalExpense.setValue(sum);
        }
    }
}