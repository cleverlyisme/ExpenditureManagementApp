package com.example.expendituremanagementapp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (checkValidate(username, password, confirmPassword)) {
                    long userId = databaseHelper.insertUser(username, password);
                    if (userId != -1) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private boolean checkValidate(String username, String password, String confirmPassword) {
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Vui lòng nhập tài khoản");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Vui lòng nhập mật khẩu");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            editTextConfirmPassword.setError("Vui lòng xác nhận mật khẩu");
            return false;
        }

        if (password.length() < 5) {
            editTextPassword.setError("Mật khẩu phải có ít nhất 5 kí tự");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            editTextPassword.setError("Mật khẩu phải chứa ít nhất một chữ cái in hoa");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Xác nhận mật khẩu không khớp");
            return false;
        }

        if (databaseHelper.checkUsername(username)) {
            editTextUsername.setError("Tên tài khoản đã được sử dụng");
            return false;
        }

        return true;
    }
}