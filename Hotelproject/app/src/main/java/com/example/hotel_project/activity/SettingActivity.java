package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

public class SettingActivity extends AppCompatActivity {

    private TextView txtChangePassword, txtChangePhoneNumber, txtLanguage, txtCountry, txtPrivacyPolicy;
    private Switch switchNotification;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting); // sử dụng layout bạn đã tạo

        // Ánh xạ view
        txtChangePassword = findViewById(R.id.changePassword);
        txtChangePhoneNumber = findViewById(R.id.changePhoneNumber);
        txtLanguage = findViewById(R.id.language);
        txtCountry = findViewById(R.id.country);
        txtPrivacyPolicy = findViewById(R.id.privacyPolicy);
        switchNotification = findViewById(R.id.switchNotification);
        btnLogout = findViewById(R.id.btnLogout);
        ImageView imgBack = findViewById(R.id.imgBack); // bạn cần thêm id cho ImageView

        imgBack.setOnClickListener(v -> {
            finish(); // Đóng SettingActivity và quay lại activity trước đó
        });
        btnLogout.setOnClickListener(v -> {
            SharedPreferencesManager.clear(getApplicationContext()); // Xóa tất cả dữ liệu
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear task stack
            startActivity(intent);
        });


        // Sự kiện bấm vào "Privacy Policy"
        txtPrivacyPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });
        switchNotification = findViewById(R.id.switchNotification);

        // Khôi phục trạng thái đã lưu trước đó
        boolean isNotifEnabled = SharedPreferencesManager.getNotificationState(this);
        switchNotification.setChecked(isNotifEnabled);

        // Lưu trạng thái khi người dùng thay đổi
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferencesManager.saveNotificationState(this, isChecked);
        });
        txtChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

    }
}
