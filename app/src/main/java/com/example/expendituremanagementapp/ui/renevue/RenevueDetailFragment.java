package com.example.expendituremanagementapp.ui.renevue;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.adapter.RenevueAdapter;
import com.example.expendituremanagementapp.model.Renevue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RenevueDetailFragment extends Fragment {

    private RenevueDetailViewModel mViewModel;
    private RecyclerView rcV;
    private RenevueAdapter adapter;
    private DatabaseHelper database;
    private TextView tvAdd;
    private ImageButton btnReload;
    //truyền serID vào đây
    private static int userID = 2;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_renevue_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_renevue_detail);
        tvAdd = view.findViewById(R.id.tv_renevue_add);
        btnReload = view.findViewById(R.id.btn_renevue_reload);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), AddRenevueActivity.class);
                startActivity(i);
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setData(list());
                adapter.notifyDataSetChanged();
            }
        });

        database = new DatabaseHelper(view.getContext());

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        rcV.addItemDecoration(decoration);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter = new RenevueAdapter(this, list());
        rcV.setAdapter(adapter);
    }
    private ArrayList<Renevue> list() {
        ArrayList<Renevue> list = new ArrayList<>();
        Cursor cursor = database.select("revenues", userID);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float price = cursor.getFloat(2);
            String note = cursor.getString(3);
            String date = cursor.getString(4);
            int userId = cursor.getInt(5);
            int renevueTypeId = cursor.getInt(6);
            list.add(new Renevue(id, name, price, note, date, userId, renevueTypeId));
        }
        return list;
    }
    public void delete(int id){
        AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity());
        dialog.setTitle("Xóa");
        dialog.setMessage("Bạn có chắc muốn xóa khoản thu không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.delete_Renevue("revenues", id);
                adapter.setData(list());
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create().show();
        return;
    }
    private float getPriceTotal(){
        float total = 0;
        Cursor cursor = database.select("revenues", userID);
        while (cursor.moveToNext()) {
            float price = cursor.getFloat(2);
            total += price;
        }
        return total;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RenevueDetailViewModel.class);
        // TODO: Use the ViewModel
    }
}