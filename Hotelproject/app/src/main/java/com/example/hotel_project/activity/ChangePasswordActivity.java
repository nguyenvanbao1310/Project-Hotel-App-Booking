package com.example.hotel_project.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.AccountApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView txtEmail;
    private EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    private Button btnChangePassword;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pasword);

        // Ánh xạ view
        txtEmail = findViewById(R.id.txtEmail);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        // Lấy thông tin email từ SharedPreferences
        AccountDTO accountDTO = SharedPreferencesManager.getAccountDTO(this);
        if (accountDTO != null && accountDTO.getEmail() != null) {
            userEmail = accountDTO.getEmail();
            txtEmail.setText("Email: " + userEmail);
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnChangePassword.setOnClickListener(v -> {
            String oldPass = edtOldPassword.getText().toString().trim();
            String newPass = edtNewPassword.getText().toString().trim();
            String confirmPass = edtConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmPass)) {
                Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới và xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(userEmail, oldPass, newPass, confirmPass);
        });
    }


    private void changePassword(String email, String oldPassword, String newPassword, String confirmPassword) {
        // Kiểm tra mật khẩu mới có đủ 6 ký tự không
        if (newPassword.length() < 6 || confirmPassword.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xác nhận mật khẩu có khớp không
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        AccountApiService apiService = RetrofitClient.getRetrofit().create(AccountApiService.class);

        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("oldPassword", oldPassword);
        request.put("newPassword", newPassword);
        request.put("confirmPassword", confirmPassword);

        Call<Map<String, String>> call = apiService.changePassword(request);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        // Parse response error để lấy message
                        String errorBody = response.errorBody().string();
                        String errorMessage = errorBody;

                        // Xử lý chuỗi JSON để lấy nội dung message
                        if (errorBody.contains("message")) {
                            errorMessage = errorBody.split("\"message\":")[1]; // Lấy phần sau "message":
                            errorMessage = errorMessage.split("\"")[1]; // Lấy nội dung giữa 2 dấu "
                        }

                        // Loại bỏ các ký tự đặc biệt nếu còn
                        errorMessage = errorMessage.replaceAll("[{}\"]", "").trim();

                        Toast.makeText(ChangePasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}