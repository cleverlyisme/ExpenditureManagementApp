package com.example.expendituremanagementapp.ui.plan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.model.Plan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PlanFragment extends Fragment {

    private EditText editPlanTitle;
    private EditText editStartDate;
    private EditText editEndDate;
    private EditText editBudgetAmount;
    private Button btnSavePlan;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance() {
        return new PlanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        editPlanTitle = view.findViewById(R.id.edit_plan_title);
        editStartDate = view.findViewById(R.id.edit_start_date);
        editEndDate = view.findViewById(R.id.edit_end_date);
        editBudgetAmount = view.findViewById(R.id.edit_budget_amount);
        btnSavePlan = view.findViewById(R.id.btn_save_plan);

        // Set up click listener for Save Plan button
        btnSavePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlan();
            }
        });

        // Initialize Calendar instance
        calendar = Calendar.getInstance();

        // Set up date listeners for start date and end date
        setupDateListeners();
    }

    private void setupDateListeners() {
        // Start Date Listener
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDate();
            }
        };

        // End Date Listener
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDate();
            }
        };

        // Set up click listeners for start date and end date fields
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(startDateListener);
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(endDateListener);
            }
        });
    }

    private void showDatePicker(DatePickerDialog.OnDateSetListener dateSetListener) {
        new DatePickerDialog(requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void updateStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String startDate = dateFormat.format(calendar.getTime());
        editStartDate.setText(startDate);
    }

    private void updateEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String endDate = dateFormat.format(calendar.getTime());
        editEndDate.setText(endDate);
    }

    private void savePlan() {
        String planTitle = editPlanTitle.getText().toString();
        String startDate = editStartDate.getText().toString();
        String endDate = editEndDate.getText().toString();
        String budgetAmountStr = editBudgetAmount.getText().toString();

        // Convert budget amount to double
        double budgetAmountValue = Double.parseDouble(budgetAmountStr);

        // Create a new Plan object
        Plan plan = new Plan();
        plan.setPlanTitle(planTitle);
        plan.setStartDate(startDate);
        plan.setEndDate(endDate);
        plan.setBudgetAmount(budgetAmountValue);

        // Save the plan to the database
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        databaseHelper.addPlan(plan);

        // Clear input fields
        editPlanTitle.setText("");
        editStartDate.setText("");
        editEndDate.setText("");
        editBudgetAmount.setText("");

        Toast.makeText(requireContext(), "Plan saved successfully", Toast.LENGTH_SHORT).show();

        // Create Intent to navigate to the new activity
        Intent intent = new Intent(requireActivity(), PlanDetailActivity.class);
        intent.putExtra("planTitle", planTitle);
        intent.putExtra("startDate", startDate);
        intent.putExtra("endDate", endDate);
        intent.putExtra("budgetAmount", budgetAmountValue);
        startActivity(intent);
    }
}