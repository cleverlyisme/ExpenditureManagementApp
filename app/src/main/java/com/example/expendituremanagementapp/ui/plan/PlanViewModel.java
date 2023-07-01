package com.example.expendituremanagementapp.ui.plan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlanViewModel extends ViewModel {

    private MutableLiveData<String> planTitle;
    private MutableLiveData<String> startDate;
    private MutableLiveData<String> endDate;
    private MutableLiveData<String> budgetAmount;

    public PlanViewModel() {
        planTitle = new MutableLiveData<>();
        startDate = new MutableLiveData<>();
        endDate = new MutableLiveData<>();
        budgetAmount = new MutableLiveData<>();
    }

    public void setPlanTitle(String title) {
        planTitle.setValue(title);
    }

    public LiveData<String> getPlanTitle() {
        return planTitle;
    }

    public void setStartDate(String date) {
        startDate.setValue(date);
    }

    public LiveData<String> getStartDate() {
        return startDate;
    }

    public void setEndDate(String date) {
        endDate.setValue(date);
    }

    public LiveData<String> getEndDate() {
        return endDate;
    }

    public void setBudgetAmount(String amount) {
        budgetAmount.setValue(amount);
    }

    public LiveData<String> getBudgetAmount() {
        return budgetAmount;
    }


}

