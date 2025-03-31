package com.example.hotel_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel_project.Api.UserApi;
import com.example.hotel_project.Api.RetrofitClient;
import com.example.hotel_project.Model.User;


public class RegisterActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        Button btnRegister = findViewById(R.id.btnCreate);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iName = ((EditText) findViewById(R.id.txtName)).getText().toString();
                String iEmail = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
                String iPhone = ((EditText) findViewById(R.id.txtPhone)).getText().toString();
                String iPassword = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

                registerUser(iName,iEmail,iPhone,iPassword);
            }
        });

    }

    private void registerUser(String iName, String iEmail, String iPhone, String iPassword)
    {
        UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);

        User user = new User();
        user.setUserName(iName);
        user.setEmail(iEmail);
        user.setPhone(iPhone);
        user.setPassWord(iPassword);

        Call<User> call = userApi.registerUser(user);

        call.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null)
                {
                    Intent intent = new Intent(RegisterActivity.this, VerifyAccountActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi đăng ký: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterActivity", "Lỗi: " + t.getMessage());
            }
        });

    }
}

