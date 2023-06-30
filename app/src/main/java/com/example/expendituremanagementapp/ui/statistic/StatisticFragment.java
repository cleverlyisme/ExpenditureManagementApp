package com.example.expendituremanagementapp.ui.statistic;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.adapter.ExpenseTypeStatisticAdapter;
import com.example.expendituremanagementapp.adapter.RevenueTypeStatisticAdapter;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.model.ExpenseTypeStatistic;
import com.example.expendituremanagementapp.model.RevenueType;
import com.example.expendituremanagementapp.model.RevenueTypeStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class StatisticFragment extends Fragment {
    private RecyclerView rvRevenueTypeStatistic, rvExpenseTypeStatistic;
    private RevenueTypeStatisticAdapter revenueTypeStatisticAdapter;
    private ExpenseTypeStatisticAdapter expenseTypeStatisticAdapter;
    private EditText etTotalRevenue, etTotalExpense;
    private DatabaseHelper database;
    private int userId = 1;

    public StatisticFragment() {}

    public static StatisticFragment newInstance() {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        database = new DatabaseHelper(view.getContext());

        etTotalRevenue = view.findViewById(R.id.etTotalRevenue);
        etTotalExpense = view.findViewById(R.id.etTotalExpense);
        rvRevenueTypeStatistic = view.findViewById(R.id.rvRevenueType);
        rvExpenseTypeStatistic = view.findViewById(R.id.rvExpenseType);

        userId = ((MainActivity) getActivity()).userID();

        etTotalRevenue.setText(String.valueOf(getTotalRevenue()));
        etTotalExpense.setText(String.valueOf(getTotalExpense()));

        revenueTypeStatisticAdapter = new RevenueTypeStatisticAdapter(getActivity());
        revenueTypeStatisticAdapter.setList(getListRevenueTypes());
        rvRevenueTypeStatistic.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        rvRevenueTypeStatistic.setAdapter(revenueTypeStatisticAdapter);

        expenseTypeStatisticAdapter = new ExpenseTypeStatisticAdapter(getActivity());
        expenseTypeStatisticAdapter.setList(getListExpenseTypes());
        rvExpenseTypeStatistic.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvExpenseTypeStatistic.setAdapter(expenseTypeStatisticAdapter);

        return view;
    }

    public Float getTotalRevenue() {
        float sum = 0;
        Cursor cursor = database.selectTotalStatistic("revenues", userId);
        if (cursor.moveToFirst()) {
            sum += cursor.getFloat(0);
        }
        return sum;
    }

    public Float getTotalExpense() {
        float sum = 0;
        Cursor cursor = database.selectTotalStatistic("expenses", userId);
        if (cursor.moveToFirst()) {
            sum += cursor.getFloat(0);
        }
        return sum;
    }

    public List<RevenueTypeStatistic> getListRevenueTypes() {
        List<RevenueTypeStatistic> lsr = new ArrayList<>();
        Cursor cursor = database.selectListTypesStatistic("revenues", "revenue_types", "revenueTypeId", userId);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float total = cursor.getFloat(2);
            lsr.add(new RevenueTypeStatistic(id, name, total));
        }
        return lsr;
    }

    public List<ExpenseTypeStatistic> getListExpenseTypes() {
        List<ExpenseTypeStatistic> lsr = new ArrayList<>();
        Cursor cursor = database.selectListTypesStatistic("expenses", "expense_types", "expenseTypeId", userId);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float total = cursor.getFloat(2);
            lsr.add(new ExpenseTypeStatistic(id, name, total));
        }
        return lsr;
    }
}