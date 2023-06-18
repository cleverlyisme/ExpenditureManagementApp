package com.example.expendituremanagementapp.ui.renevue;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expendituremanagementapp.R;

public class RenevueTypeFragment extends Fragment {

    private RenevueTypeViewModel mViewModel;

    public static RenevueTypeFragment newInstance() {
        return new RenevueTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_renevue_type, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RenevueTypeViewModel.class);
        // TODO: Use the ViewModel
    }

}