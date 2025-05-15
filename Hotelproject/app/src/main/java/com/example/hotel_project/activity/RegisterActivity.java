package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.RegisterApiService;
import com.example.hotel_project.model.RegisterRequest;
import com.example.hotel_project.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    EditText txtName, txtCCCD, txtAddress, txtEmail, txtPhone, txtPassword;
    Spinner spinnerGender;
    Button btnRegister;

    RegisterApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 1. Ánh xạ view
        txtName = findViewById(R.id.txtName);
        txtCCCD = findViewById(R.id.txtCCCD);
        txtAddress = findViewById(R.id.txtAddress);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnRegister = findViewById(R.id.btnCreate);

        apiService = RetrofitClient.getRetrofit().create(RegisterApiService.class);
        TextView textOrSignIn = findViewById(R.id.textView);

        textOrSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            RegisterRequest request = new RegisterRequest();
            request.setFullname(txtName.getText().toString());
            request.setGender(spinnerGender.getSelectedItem().toString().equals("Male"));
            request.setAddress(txtAddress.getText().toString());
            request.setCccd(txtCCCD.getText().toString());
            request.setEmail(txtEmail.getText().toString());
            request.setPhone(txtPhone.getText().toString());
            request.setUsername(txtEmail.getText().toString());
            request.setPassword(txtPassword.getText().toString());

            // Gửi yêu cầu đăng ký
            apiService.register(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Chuyển qua màn hình OTP
                        Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                        intent.putExtra("email", txtEmail.getText().toString()); // truyền email để xác nhận OTP
                        startActivity(intent);
                        finish(); // Đóng màn hình đăng ký
                    } else {
                        try {
                            // Đọc nội dung lỗi (ví dụ: "Email đã tồn tại!")
                            String errorMessage = response.errorBody().string();
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}

