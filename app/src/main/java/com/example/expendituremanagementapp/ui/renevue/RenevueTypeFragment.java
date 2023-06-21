package com.example.expendituremanagementapp.ui.renevue;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.adapter.RenevueTypeAdapter;
import com.example.expendituremanagementapp.model.Renevue;
import com.example.expendituremanagementapp.model.RenevueType;

import java.util.ArrayList;
import java.util.List;

public class RenevueTypeFragment extends Fragment {
    private RenevueTypeViewModel mViewModel;
    private RecyclerView rcV;
    private RenevueTypeAdapter adapter;
    private ArrayList<RenevueType> arrayList;
    private TextView tvAdd;

    public static RenevueTypeFragment newInstance() {
        return new RenevueTypeFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_renevue_type);
        adapter = new RenevueTypeAdapter(view.getContext());
        arrayList = new ArrayList<>();
        tvAdd = view.findViewById(R.id.tv_renevue_type_add);

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_renevue_type);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = dialog.getWindow();
                if(window != null){
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                EditText edtName = dialog.findViewById(R.id.edt_renevue_type_name);
                Button btnAdd = dialog.findViewById(R.id.btn_renevue_type_add);
                Button btnCanel = dialog.findViewById(R.id.btn_renevue_type_canel);
                btnCanel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(view.getContext(), "Xin chào bạn", Toast.LENGTH_SHORT).show();
                        String name = edtName.getText().toString().trim();
                        if(name.isEmpty()){
                            Toast.makeText(view.getContext(), "Bạn chưa nhập nội dung!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }
                        Toast.makeText(view.getContext(), "Bạn đã thêm thành công!", Toast.LENGTH_SHORT).show();
                        arrayList.add(new RenevueType(1, 1, ""+name+""));
//                        actionGetData();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                    dialog.show();
            }
        });

        rcV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Xin chào", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter.setData(arrayList);
        rcV.setAdapter(adapter);

        actionGetData();
    }

    private void actionGetData(){
        arrayList.add(new RenevueType(1, 1, "Lương"));
        arrayList.add(new RenevueType(1, 1, "Thu nhập khác"));

        adapter.notifyDataSetChanged();
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