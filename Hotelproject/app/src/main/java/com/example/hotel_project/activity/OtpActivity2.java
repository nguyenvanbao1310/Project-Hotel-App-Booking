package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class OtpActivity2 extends AppCompatActivity {
    EditText txtOtp1, txtOtp2, txtOtp3, txtOtp4;
    private Button btnVerify, btnChange;
    private String email;
    private OtpApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyaccount2);

        // Khởi tạo Retrofit API service
        apiService = RetrofitClient.getRetrofit().create(OtpApiService.class);

        // Ánh xạ các EditText cho OTP
        txtOtp1 = findViewById(R.id.txtOtp1);
        txtOtp2 = findViewById(R.id.txtOtp2);
        txtOtp3 = findViewById(R.id.txtOtp3);
        txtOtp4 = findViewById(R.id.txtOtp4);

        btnVerify = findViewById(R.id.btnSubmit);
        btnChange =  findViewById(R.id.btnChange);


        // Lấy email từ màn hình trước
        email = getIntent().getStringExtra("email");
        // Gọi hàm này để tự động chuyển focus
        setupOtpInputs();

        btnVerify.setOnClickListener(v -> {
            // Lấy giá trị OTP từ 4 ô nhập
            String otp1 = txtOtp1.getText().toString().trim();
            String otp2 = txtOtp2.getText().toString().trim();
            String otp3 = txtOtp3.getText().toString().trim();
            String otp4 = txtOtp4.getText().toString().trim();

            // Kiểm tra xem tất cả các ô nhập OTP đã được điền đầy đủ chưa
            if (otp1.isEmpty() || otp2.isEmpty() || otp3.isEmpty() || otp4.isEmpty()) {
                Toast.makeText(OtpActivity2.this, "Please enter all OTP digits.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ghép các giá trị OTP lại thành một chuỗi
            String otp = otp1 + otp2 + otp3 + otp4;
            Log.d("OTP_CHECK", "OTP Entered: " + otp + ", Email: " + email);
            // Gửi yêu cầu xác thực OTP
            verifyOtp(email, otp);
        });
        btnChange.setOnClickListener(v -> {
            if (email == null || email.isEmpty()) {
                Toast.makeText(OtpActivity2.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> resendRequest = new HashMap<>();
            resendRequest.put("email", email);

            apiService.resendOtp(resendRequest).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(OtpActivity2.this, "Đã gửi lại mã OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OtpActivity2.this, "Không thể gửi lại mã OTP", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Toast.makeText(OtpActivity2.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void verifyOtp(String email, String otp) {
        // Tạo request data
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("otp", otp);

        // Gọi API xác nhận OTP
        apiService.XacNhanOtp(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Đăng ký thành công, chuyển về màn hình đăng nhập
                    Toast.makeText(OtpActivity2.this, "Thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpActivity2.this, ResetPasswordActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish(); // Đóng màn hình OTP
                } else {
                    Toast.makeText(OtpActivity2.this, "Mã OTP không chính xác", Toast.LENGTH_SHORT).show();
                    txtOtp1.setText("");
                    txtOtp2.setText("");
                    txtOtp3.setText("");
                    txtOtp4.setText("");
                    txtOtp1.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(OtpActivity2.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupOtpInputs() {
        txtOtp1.addTextChangedListener(new GenericTextWatcher(txtOtp1, txtOtp2));
        txtOtp2.addTextChangedListener(new GenericTextWatcher(txtOtp2, txtOtp3));
        txtOtp3.addTextChangedListener(new GenericTextWatcher(txtOtp3, txtOtp4));
        txtOtp4.addTextChangedListener(new GenericTextWatcher(txtOtp4, null));
    }

    private class GenericTextWatcher implements android.text.TextWatcher {
        private final EditText currentView;
        private final EditText nextView;

        public GenericTextWatcher(EditText currentView, EditText nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && nextView != null) {
                nextView.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(android.text.Editable s) {}
    }

}

