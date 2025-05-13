package com.example.hotel_project.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.LoginApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.LoginRequest;
import com.example.hotel_project.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            String emailOrPhone = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (emailOrPhone.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest loginRequest = new LoginRequest(emailOrPhone, password);

            // Gọi API login
            LoginApiService apiService = RetrofitClient.getRetrofit().create(LoginApiService.class);
            Call<AccountDTO> call = apiService.login(loginRequest);

            call.enqueue(new Callback<AccountDTO>() {
                @Override
                public void onResponse(Call<AccountDTO> call, Response<AccountDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AccountDTO account = response.body();
                        // Xử lý khi login thành công
                        Toast.makeText(LoginActivity.this, "Login Success: " + account.getUsername(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý khi login thất bại
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AccountDTO> call, Throwable t) {
                    // Xử lý khi có lỗi xảy ra
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
