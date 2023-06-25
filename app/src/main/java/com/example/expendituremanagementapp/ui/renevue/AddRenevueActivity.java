package com.example.expendituremanagementapp.ui.renevue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.model.Renevue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddRenevueActivity extends AppCompatActivity {
    private DatabaseHelper database;
    private AutoCompleteTextView auto_name;
    private EditText edtPrice, edtNote, edtDate;
    private Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_renevue);

        auto_name = findViewById(R.id.auto_renevue_add_name);
        edtPrice = findViewById(R.id.edt_renevue_add_price);
        edtNote = findViewById(R.id.edt_renevue_add_note);
        edtDate = findViewById(R.id.edt_renevue_add_date);

        btnAdd = findViewById(R.id.btn_renevue_add);
        btnCancel = findViewById(R.id.btn_renevue_add_cancel);

        database = new DatabaseHelper(this);
        //Hiện tên
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, options());
        auto_name.setAdapter(adapter);
        auto_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_name.showDropDown();
            }
        });

        //Hiện thời gian
        final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDate currentDate = LocalDate.now();
        edtDate.setText(currentDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Xử lý ngày được chọn
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, monthOfYear, dayOfMonth);

                // Hiển thị giá trị ngày được chọn trên EditText
                edtDate.setText(inputDateFormat.format(selectedCalendar.getTime()));
            }
        }, year, month, day);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(AddRenevueActivity.this, "Xin chao", Toast.LENGTH_SHORT).show();
                String name = auto_name.getText().toString().trim();
                String note = edtNote.getText().toString().trim();
                String date = edtDate.getText().toString().trim();
                if(name.isEmpty() || edtPrice.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddRenevueActivity.this, "Bạn phải nhập đầy đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                }
                else{
                    float price = Float.parseFloat(edtPrice.getText().toString().trim());
                    Renevue renevue = new Renevue(name, price, note, date, 2, 2);
                    database.insert_Renevue(renevue);
                    Toast.makeText(AddRenevueActivity.this, "Bạn đã thêm thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String[] options(){
        String[] option = new String[0];
        Cursor cursor = database.select("revenue_types");
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            option = Arrays.copyOf(option, option.length + 1);
            option[option.length -1] = name;
        }
        return option;
    }
}