package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.LoginApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.LoginRequest;
import com.example.hotel_project.model.LoginResponse;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private LoginApiService apiService;
    private TextView viewAsGuest;
    private CheckBox keepMeLogin;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // thay bằng layout bạn dán ở trên

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        viewAsGuest = findViewById(R.id.viewAsGuest);
//        LinearLayout googleButton = findViewById(R.id.googleButton);
//
//        //googleLoginButton = findViewById(R.id.googleButton);
//        // Cấu hình Google Sign-In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id)) // Sử dụng Client ID từ Google Cloud
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        googleButton.setOnClickListener(v -> signInWithGoogle());

        apiService = RetrofitClient.getRetrofit().create(LoginApiService.class);

        loginButton.setOnClickListener(v -> performLogin());
        // Khi nhấn "Don't have an account?", chuyển sang màn hình đăng ký
        viewAsGuest.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        // Trong onCreate() của LoginActivity
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        keepMeLogin = findViewById(R.id.keepMeLogin);

// Tự động điền nếu đã lưu
        if (SharedPreferencesManager.isKeepMeLogin(this)) {
            emailInput.setText(SharedPreferencesManager.getSavedEmail(this));
            passwordInput.setText(SharedPreferencesManager.getSavedPassword(this));
            keepMeLogin.setChecked(true);
        }

    }
//    private void signInWithGoogle() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            if (task.isSuccessful()) {
//                GoogleSignInAccount account = task.getResult();
//                // Đăng nhập thành công, có thể truy cập thông tin người dùng
//                String idToken = account.getIdToken();
//                handleSignIn(idToken);
//            } else {
//                // Đăng nhập thất bại
//                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void handleSignIn(String idToken) {
//        // Gửi idToken lên backend hoặc xử lý theo cách bạn muốn
//        // Ví dụ: Gửi lên Google Cloud để xác thực thêm hoặc lưu trữ thông tin
//        Toast.makeText(this, "Đăng nhập thành công: " + idToken, Toast.LENGTH_SHORT).show();
//        // Chuyển sang màn hình chính
//        Intent intent = new Intent(LoginActivity.this, HotelActivity.class);
//        startActivity(intent);
//        finish();
//    }
    private void performLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest request = new LoginRequest(email, password);

        Call<LoginResponse> call = apiService.login(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    AccountDTO account = loginResponse.getAccount();
                    GuestDTO guest = loginResponse.getGuest(); // giả sử API trả về GuestDTO
                    Log.d("LoginDebug", "Account: " + account);
                    Log.d("LoginDebug", "Guest from API: " + guest);
                    if (guest != null) {
                        SharedPreferencesManager.saveGuestDTO(LoginActivity.this, guest);
                    } else {
                        Log.d("LoginDebug", "Guest is null, không lưu được.");
                    }

                    SharedPreferencesManager.saveAccountDTO(LoginActivity.this, account);
                    SharedPreferencesManager.saveGuestDTO(LoginActivity.this, guest); // Lưu GuestDTO

                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HotelActivity.class);
                    startActivity(intent);
                    finish();
                } else if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        SharedPreferencesManager.saveLoginInfo(
                LoginActivity.this,
                email,
                password,
                keepMeLogin.isChecked()
        );

    }
}

