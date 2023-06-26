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
import androidx.recyclerview.widget.DividerItemDecoration;
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
    private TextView tvAdd;
    private DatabaseHelper database;
    //truyền serID vào đây
    private static int userId = 2;

    public static RenevueTypeFragment newInstance() {
        return new RenevueTypeFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_renevue_type);
//        adapter = new RenevueTypeAdapter(this, arrayList);
        tvAdd = view.findViewById(R.id.tv_renevue_type_add);

        database = new DatabaseHelper(view.getContext());

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
                Button btnCanel = dialog.findViewById(R.id.btn_renevue_type_add_cancel);
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
                        database.insert_Renevue_Type(name, userId);
                        adapter.setData(getList());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                    dialog.show();
            }
        });

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        rcV.addItemDecoration(decoration);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter = new RenevueTypeAdapter(this, getList());
        rcV.setAdapter(adapter);
    }

    private ArrayList<RenevueType> getList(){
        ArrayList<RenevueType> arrayList = new ArrayList<>();
        Cursor cursor1 = database.select("revenue_types", 1);
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String name = cursor1.getString(1);
            int userId = cursor1.getInt(2);
            arrayList.add(new RenevueType(id, userId, name));
        }
        if(userId != 1){
            Cursor cursor = database.select("revenue_types", userId);
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int userId = cursor.getInt(2);
                arrayList.add(new RenevueType(id, userId, name));
            }
        }
        return arrayList;
    }

    public void delete(String name, int id){
        AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity());
        dialog.setTitle("Xóa");
        dialog.setMessage("Bạn có chắc muốn xóa "+name+" không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.delete_Renevue("revenue_types", id);
                Toast.makeText(getContext(), "Bạn đã xóa  loại thu thành công!", Toast.LENGTH_SHORT).show();
                adapter.setData(getList());
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
    }
    public void edit(String ten, int id){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_renevue_type_edit);
        Window window = dialog.getWindow();
        if(window != null){
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        EditText edtName = dialog.findViewById(R.id.edt_renevue_type_edit_name);
        Button btnEdit = dialog.findViewById(R.id.btn_renevue_type_edit);
        Button btnCanel = dialog.findViewById(R.id.btn_renevue_type_edit_cancel);

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
                if(name.isEmpty()){
                    Toast.makeText(v.getContext(), "Bạn phải nhập dữ liệu!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                else {
                    RenevueType renevueType = new RenevueType(id, name);
                    database.update_Renevue_Type(renevueType);
                    Toast.makeText(getContext(), "Bạn đã sửa thành công!", Toast.LENGTH_SHORT).show();
                    adapter.setData(getList());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_renevue_type, container, false);
    }
    public boolean checkUserId(int id){
        if(id == userId)
            return true;
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RenevueTypeViewModel.class);
        // TODO: Use the ViewModel
    }

}