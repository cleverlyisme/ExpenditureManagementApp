package com.example.expendituremanagementapp.ui.statistic;

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

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.adapter.ExpenseTypeStatisticRecyclerView;
import com.example.expendituremanagementapp.adapter.RenevueTypeStatisticRecyclerView;
import com.example.expendituremanagementapp.model.ExpenseTypeStatistic;
import com.example.expendituremanagementapp.model.RenevueTypeStatistic;

import java.util.List;

public class StatisticFragment extends Fragment {
    private RecyclerView rvRenevueTypeStatistic, rvExpenseTypeStatistic;
    private RenevueTypeStatisticRecyclerView renevueTypeStatisticAdapter;
    private ExpenseTypeStatisticRecyclerView expenseTypeStatisticAdapter;
    private StatisticViewModel statisticViewModel;
    private EditText etTotalRenevue, etTotalExpense;

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

        etTotalRenevue = view.findViewById(R.id.etTotalRenevue);
        etTotalExpense = view.findViewById(R.id.etTotalExpense);
        rvRenevueTypeStatistic = view.findViewById(R.id.rvRenevueType);
        rvExpenseTypeStatistic = view.findViewById(R.id.rvExpenseType);

        statisticViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);

        renevueTypeStatisticAdapter = new RenevueTypeStatisticRecyclerView(getActivity());
        rvRenevueTypeStatistic.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRenevueTypeStatistic.setAdapter(renevueTypeStatisticAdapter);

        expenseTypeStatisticAdapter = new ExpenseTypeStatisticRecyclerView(getActivity());
        rvExpenseTypeStatistic.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvExpenseTypeStatistic.setAdapter(expenseTypeStatisticAdapter);
        statisticViewModel.getTotalRenevue().observe(getActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                etTotalRenevue.setText(String.valueOf(total));
            }
        });

        statisticViewModel.getTotalExpense().observe(getActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                etTotalExpense.setText(String.valueOf(total));
            }
        });

        statisticViewModel.getListRenevueTypeStatistic().observe(getActivity(), new Observer<List<RenevueTypeStatistic>>() {
            @Override
            public void onChanged(List<RenevueTypeStatistic> lsr) {
                renevueTypeStatisticAdapter.setList(lsr);
            }
        });

        statisticViewModel.getListExpenseTypeStatistic().observe(getActivity(), new Observer<List<ExpenseTypeStatistic>>() {
            @Override
            public void onChanged(List<ExpenseTypeStatistic> lst) {
                expenseTypeStatisticAdapter.setList(lst);
            }
        });

        return view;
    }
}