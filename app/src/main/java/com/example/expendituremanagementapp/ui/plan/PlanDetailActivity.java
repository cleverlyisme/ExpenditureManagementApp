package com.example.expendituremanagementapp.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.adapter.ItemListAdapter;
import com.example.expendituremanagementapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailActivity extends AppCompatActivity {

    private TextView tvPlanTitle;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvBudgetAmount;
    private String planTitle;
    private String startDate;
    private String endDate;
    private double budgetAmount;

    private EditText etItemName;
    private EditText etItemAmount;
    private Button btnAddItem;
    private ListView listItems;
    private TextView tvTotalAmount;
    private Button btnSave;
    private Button btnDeleteItem;

    private ItemListAdapter itemListAdapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        // Initialize views
        tvPlanTitle = findViewById(R.id.tvPlanTitle);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvBudgetAmount = findViewById(R.id.tvBudgetAmount);
        etItemName = findViewById(R.id.etItemName);
        etItemAmount = findViewById(R.id.etItemAmount);
        btnAddItem = findViewById(R.id.btnAddItem);
        listItems = findViewById(R.id.listItems);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnSave = findViewById(R.id.btnSave);
        btnDeleteItem = findViewById(R.id.btnDeleteItem);

        // Set click listener for Add Item button
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        // Set click listener for Delete Item button
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        // Set click listener for Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlan();
            }
        });

        // Initialize item list
        itemList = new ArrayList<>();
        itemListAdapter = new ItemListAdapter(this, itemList);
        listItems.setAdapter(itemListAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            planTitle = extras.getString("planTitle");
            startDate = extras.getString("startDate");
            endDate = extras.getString("endDate");
            budgetAmount = extras.getDouble("budgetAmount");

            // Display plan details in TextViews
            tvPlanTitle.setText(planTitle);
            tvStartDate.setText(startDate);
            tvEndDate.setText(endDate);
            tvBudgetAmount.setText(String.valueOf(budgetAmount));
        }
    }

    private void addItem() {
        String itemName = etItemName.getText().toString().trim();
        String itemAmountStr = etItemAmount.getText().toString().trim();

        if (itemName.isEmpty() || itemAmountStr.isEmpty()) {
            Toast.makeText(this, "Please enter item name and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double itemAmount = Double.parseDouble(itemAmountStr);

        Item item = new Item(itemName, (int) itemAmount);
        itemList.add(item);
        itemListAdapter.notifyDataSetChanged();

        updateTotalAmount();
        clearItemFields();
    }

    private void deleteItem() {
        itemList.clear();
        itemListAdapter.notifyDataSetChanged();

        updateTotalAmount();
    }

    private void updateTotalAmount() {
        double totalAmount = 0;
        for (Item item : itemList) {
            totalAmount += item.getAmount();
        }
        tvTotalAmount.setText("Total Amount: $" + totalAmount);
    }

    private void clearItemFields() {
        etItemName.setText("");
        etItemAmount.setText("");
    }

    private void savePlan() {
        // Get plan details from the views
        String planTitle = tvPlanTitle.getText().toString().trim();
        String startDate = tvStartDate.getText().toString().trim();
        String endDate = tvEndDate.getText().toString().trim();
        double budgetAmount = Double.parseDouble(tvBudgetAmount.getText().toString().trim());

        // TODO: Implement your save plan logic here
        // Calculate total amount from the item list
        double totalAmount = calculateTotalAmount();

        // Check if total amount exceeds budget amount
        if (totalAmount > budgetAmount) {
            Toast.makeText(this, "Total amount exceeds the budget", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Plan saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private double calculateTotalAmount() {
        double totalAmount = 0;
        for (Item item : itemList) {
            totalAmount += item.getAmount();
        }
        return totalAmount;
    }
}

