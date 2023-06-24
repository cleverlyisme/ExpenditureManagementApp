package com.example.expendituremanagementapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expendituremanagementapp.ui.renevue.RenevueDetailFragment;
import com.example.expendituremanagementapp.ui.renevue.RenevueTypeFragment;

public class RenevueViewPage2Adapter extends FragmentStateAdapter {
    public RenevueViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position == 0){
            fragment = new RenevueDetailFragment();
        }else {
            fragment = new RenevueTypeFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
