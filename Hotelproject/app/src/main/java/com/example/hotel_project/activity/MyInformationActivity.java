package com.example.hotel_project.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.GuestApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInformationActivity extends AppCompatActivity {

    private EditText txtFullName, txtUserName, txtEmail, txtPhone, txtAddress;

    private TextView editText;

    private Button btnSave;
    private GuestApiService guestApiService;
    private ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        guestApiService = RetrofitClient.getRetrofit().create(GuestApiService.class);
        imgBack = findViewById(R.id.imgBack);
        txtFullName = findViewById(R.id.txtFullName);
        txtUserName = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);

        GuestDTO guestDTO = SharedPreferencesManager.getGuestDTO(this);
        AccountDTO accountDTO = SharedPreferencesManager.getAccountDTO(this);

        txtFullName.setText(guestDTO.getFullname());
        txtUserName.setText(accountDTO.getUsername());
        txtEmail.setText(accountDTO.getEmail());
        txtPhone.setText(accountDTO.getPhone());
        txtAddress.setText(guestDTO.getAddress());

        editText = findViewById(R.id.txtEdit);
        btnSave = findViewById(R.id.btnSave);

        setEditable(false);
        editText.setOnClickListener(v -> {
            setEditable(true);
        });
        imgBack.setOnClickListener(v -> {
            finish();  // Đóng activity hiện tại và quay lại activity trước đó
        });
        btnSave.setOnClickListener(v -> {
            String fullName = txtFullName.getText().toString().trim();
            String userName = txtUserName.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String phone = txtPhone.getText().toString().trim();
            String address = txtAddress.getText().toString().trim();

            // Validate: Không được để trống
            if (fullName.isEmpty() || userName.isEmpty() || email.isEmpty()
                    || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng không để trống bất kỳ trường nào.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate: Số điện thoại phải đủ 10 số
            if (!phone.matches("\\d{10}")) {
                Toast.makeText(this, "Số điện thoại phải gồm đúng 10 chữ số.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate: Email phải kết thúc bằng @gmail.com
            if (!email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Email phải kết thúc bằng @gmail.com.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gán giá trị mới
            if (guestDTO != null) {
                guestDTO.setFullname(fullName);
                guestDTO.setAddress(address);
            }

            if (accountDTO != null) {
                accountDTO.setUsername(userName);
                accountDTO.setEmail(email);
                accountDTO.setPhone(phone);
            }

            // Gọi API cập nhật Guest
            if (guestDTO != null) {
                guestApiService.updateGuest(guestDTO.getId(), guestDTO).enqueue(new Callback<GuestDTO>() {
                    @Override
                    public void onResponse(Call<GuestDTO> call, Response<GuestDTO> response) {
                        if (response.isSuccessful()) {
                            // Lưu lại Guest mới vào SharedPreferences
                            SharedPreferencesManager.saveGuestDTO(MyInformationActivity.this, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GuestDTO> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            // Gọi API cập nhật Account
            if (accountDTO != null) {
                guestApiService.updateAccount(accountDTO.getId(), accountDTO).enqueue(new Callback<AccountDTO>() {
                    @Override
                    public void onResponse(Call<AccountDTO> call, Response<AccountDTO> response) {
                        if (response.isSuccessful()) {
                            // THÊM DÒNG NÀY: Lưu lại Account mới vào SharedPreferences
                            SharedPreferencesManager.saveAccountDTO(MyInformationActivity.this, response.body());


                        }
                    }

                    @Override
                    public void onFailure(Call<AccountDTO> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            setEditable(false);
        });



    }
    private void setEditable(boolean enabled) {
        txtFullName.setEnabled(enabled);
        txtUserName.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
        txtPhone.setEnabled(enabled);
        txtAddress.setEnabled(enabled);
    }
}
