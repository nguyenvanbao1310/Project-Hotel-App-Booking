package com.example.hotel_project.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.OtpApiService;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText txtEmail;
    private Button btnSubmit;
    private OtpApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword); // XML bạn đã gửi

        txtEmail = findViewById(R.id.txtEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        apiService = RetrofitClient.getRetrofit().create(OtpApiService.class);

        btnSubmit.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            sendOtpRequest(email);
        });
    }

    private void sendOtpRequest(String email) {
        Map<String, String> request = new HashMap<>();
        request.put("email", email);

        apiService.resendOtp(request).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "OTP sent to your email", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity2.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Email not found or cannot send OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
