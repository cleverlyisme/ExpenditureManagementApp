package com.example.expendituremanagementapp.ui.renevue;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.adapter.RenevueTypeAdapter;
import com.example.expendituremanagementapp.model.RenevueType;

import java.util.ArrayList;

public class RenevueTypeFragment extends Fragment {
    private RenevueTypeViewModel mViewModel;
    private RecyclerView rcV;
    private RenevueTypeAdapter adapter;
    private ArrayList<RenevueType> arrayList;
    private TextView tvAdd;
    private DatabaseHelper database;

    public static RenevueTypeFragment newInstance() {
        return new RenevueTypeFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_renevue_type);
        arrayList = new ArrayList<>();
        adapter = new RenevueTypeAdapter(this, arrayList);
        tvAdd = view.findViewById(R.id.tv_renevue_type_add);

        database = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = database.getWritableDatabase();

        if (check(1)){
            db.execSQL("INSERT INTO revenue_types VALUES(1, 'Lương', 1)");
        }
        if (check(2)){
            db.execSQL("INSERT INTO revenue_types VALUES(2, 'Thu nhập khác', 1)");
        }

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_renevue_type_add);
                Window window = dialog.getWindow();
                if(window != null){
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                EditText edtName = dialog.findViewById(R.id.edt_renevue_type_add_name);
                Button btnAdd = dialog.findViewById(R.id.btn_renevue_type_add);
                Button btnCanel = dialog.findViewById(R.id.btn_renevue_type_add_canel);
                btnCanel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString().trim();
                        if(name.isEmpty()){
                            Toast.makeText(view.getContext(), "Bạn chưa nhập nội dung!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }
                        Toast.makeText(view.getContext(), "Bạn đã thêm thành công!", Toast.LENGTH_SHORT).show();
                        db.execSQL("INSERT INTO revenue_types VALUES(null, '"+name+"', 1)");
                        actionGetData();
                        dialog.dismiss();
                    }
                });
                    dialog.show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter.setData(arrayList);
        rcV.setAdapter(adapter);

        actionGetData();
    }

    private void actionGetData(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM revenue_types", null);
        arrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int userId = cursor.getInt(2);
            arrayList.add(new RenevueType(id, userId, name));
            adapter.notifyDataSetChanged();
        }
    }

    public void delete(String name, int i){
        AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity());
        dialog.setTitle("Xóa");
        dialog.setMessage("Bạn có chắc muốn xóa "+name+" không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = database.getWritableDatabase();
                db.execSQL("DELETE FROM revenue_types WHERE id = '"+i+"'");
                actionGetData();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        return;
    }
    public void edit(String ten, int i){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_renevue_type_edit);
        Window window = dialog.getWindow();
        if(window != null){
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        EditText edtName = dialog.findViewById(R.id.edt_renevue_type_edit_name);
        Button btnEdit = dialog.findViewById(R.id.btn_renevue_type_edit);
        Button btnCanel = dialog.findViewById(R.id.btn_renevue_type_edit_canel);

        edtName.setText(ten);
        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
//                Toast.makeText(v.getContext(), "Chỉnh sửa", Toast.LENGTH_SHORT).show();
                if(name.isEmpty()){
                    Toast.makeText(v.getContext(), "Bạn phải nhập dữ liệu!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                else {
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.execSQL("UPDATE revenue_types SET name='"+name+"' WHERE id='"+i+"'");
                    dialog.dismiss();
                    actionGetData();
                }
            }
        });
        dialog.show();
    }

    private boolean check(int i){
        SQLiteDatabase db1 = database.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT id FROM revenue_types", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            if ( i == id)
                return false;
        }
        return true;
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