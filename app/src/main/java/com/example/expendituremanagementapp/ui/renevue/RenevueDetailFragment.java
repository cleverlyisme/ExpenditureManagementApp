package com.example.expendituremanagementapp.ui.renevue;

import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.adapter.RenevueAdapter;
import com.example.expendituremanagementapp.model.Renevue;

import java.time.LocalDate;
import java.util.ArrayList;

public class RenevueDetailFragment extends Fragment {

    private RenevueDetailViewModel mViewModel;
    private RecyclerView rcV;
    private RenevueAdapter adapter;
    private ArrayList<Renevue> arrayList;
    private DatabaseHelper database;
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
        arrayList =new ArrayList<>();

        database = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = database.getWritableDatabase();
        database.onCreate(db);
//        db.execSQL("INSERT INTO revenues VALUES(null, 'Lương', 123000, 'xin chào', date('now'), 1, 1)");
//        db.execSQL("DELETE FROM revenues");
//        db.execSQL("INSERT INTO revenues VALUES(null, 'Lương', 123000, 'xin chao toi la nguyen', date('now'), 1, 1)");

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter = new RenevueAdapter(view.getContext(), arrayList);
        rcV.setAdapter(adapter);
        actionGetData();
    }

    private void actionGetData(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM revenues", null);
        arrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float price = cursor.getFloat(2);
            String note = cursor.getString(3);
            LocalDate date = LocalDate.parse(cursor.getString(4));
            int userId = cursor.getInt(5);
            int renevueTypeId = cursor.getInt(6);
            arrayList.add(new Renevue(id, name, price, note, date, userId, renevueTypeId));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RenevueDetailViewModel.class);
        // TODO: Use the ViewModel
    }
}