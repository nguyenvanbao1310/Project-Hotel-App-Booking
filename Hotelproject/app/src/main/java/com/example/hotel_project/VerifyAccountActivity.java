    package com.example.hotel_project;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;
    import java.util.HashMap;
    import java.util.Map;
    import com.example.hotel_project.Api.EmailApi;
    import com.example.hotel_project.Api.RetrofitClient;

    import androidx.appcompat.app.AppCompatActivity;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;


    public class VerifyAccountActivity extends AppCompatActivity
    {
        private String email;
        private String name;
        private String phone;
        private String password;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verifyaccount);

            email = getIntent().getStringExtra("email");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            password = getIntent().getStringExtra("password");


            Button btnVerify = findViewById(R.id.btnChange);

            btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText otpEditText1 = findViewById(R.id.txtOtp1);
                    EditText otpEditText2 = findViewById(R.id.txtOtp2);
                    EditText otpEditText3 = findViewById(R.id.txtOtp3);
                    EditText otpEditText4 = findViewById(R.id.txtOtp4);

                    String otp = otpEditText1.getText().toString() +
                            otpEditText2.getText().toString() +
                            otpEditText3.getText().toString() +
                            otpEditText4.getText().toString();
                    if (otp.length() == 4) {
                        verifyOtp(otp);
                    } else {
                        Toast.makeText(VerifyAccountActivity.this, "Vui lòng nhập đầy đủ mã OTP", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void verifyOtp(String otp)
        {
            Map<String, String> otpData = new HashMap<>();
            otpData.put("email", email);
            otpData.put("otp", otp);
            otpData.put("userName", name);
            otpData.put("phone", phone);
            otpData.put("passWord", password);

            EmailApi emailApi = RetrofitClient.getRetrofitInstance().create(EmailApi.class);
            Call<String> call = emailApi.verifyOtp(otpData);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(VerifyAccountActivity.this, response.body(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(VerifyAccountActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(VerifyAccountActivity.this, "Mã OTP không chính xác.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(VerifyAccountActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
