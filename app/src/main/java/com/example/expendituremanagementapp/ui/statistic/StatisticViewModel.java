package com.example.expendituremanagementapp.ui.statistic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expendituremanagementapp.model.Renevue;

public class StatisticViewModel extends AndroidViewModel {
//    private LiveData<Float> totalRenevue;
    public StatisticViewModel(@NonNull Application application) {
        super(application);

//        totalRenevue = Renevue.getTotalRenevue();
    }
}
