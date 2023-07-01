package com.example.expendituremanagementapp.model;

public class Plan {
    private int id;
    private String planTitle;
    private String startDate;
    private String endDate;
    private double budgetAmount;

    // Constructors

    public String getPlanTitle() {
        return planTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    // Setter methods

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
}
