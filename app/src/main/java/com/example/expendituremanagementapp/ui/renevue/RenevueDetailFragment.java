package com.example.expendituremanagementapp.ui.renevue;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.adapter.RenevueAdapter;
import com.example.expendituremanagementapp.model.Renevue;

import java.util.ArrayList;
import java.util.List;

public class RenevueDetailFragment extends Fragment {

    private RenevueDetailViewModel mViewModel;
    private RecyclerView rcV;
    private RenevueAdapter adapter;

    public static RenevueDetailFragment newInstance() {
        return new RenevueDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_renevue_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_renevue_detail);
        adapter = new RenevueAdapter(view.getContext());

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter.setData(getListRenevue());

        rcV.setAdapter(adapter);
    }

    private List<Renevue> getListRenevue(){
        List<Renevue> list = new ArrayList<>();
//        list.add(new Renevue(1, 1, "nguyen", "khong", 123000));
//        list.add(new Renevue(2, 2, "Tran", "khong", 1234000));
//        list.add(new Renevue(3, 3, "Quang", "khong", 12345000));

        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RenevueDetailViewModel.class);
        // TODO: Use the ViewModel
    }
}