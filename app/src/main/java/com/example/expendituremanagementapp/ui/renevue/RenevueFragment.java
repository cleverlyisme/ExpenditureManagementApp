package com.example.expendituremanagementapp.ui.renevue;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.adapter.RenevueViewPage2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RenevueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RenevueFragment extends Fragment {
    private TabLayout mTl;
    private ViewPager2 mVp;

    public RenevueFragment() {
        // Required empty public constructor
    }

    public static RenevueFragment newInstance(String param1, String param2) {
        RenevueFragment fragment = new RenevueFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTl = view.findViewById(R.id.tab_Layout);
        mVp = view.findViewById(R.id.view_Pager2);

        RenevueViewPage2Adapter adapter = new RenevueViewPage2Adapter(getActivity());
        mVp.setAdapter(adapter);

        new TabLayoutMediator(mTl, mVp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position  == 0){
                    tab.setText("Renevue Detail");
                    tab.setIcon(R.drawable.ic_menu_camera);
                }
                else{
                    tab.setText("Renevue Type");
                    tab.setIcon(R.drawable.ic_menu_camera);
                }
            }
        }).attach();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_renevue, container, false);
    }
}