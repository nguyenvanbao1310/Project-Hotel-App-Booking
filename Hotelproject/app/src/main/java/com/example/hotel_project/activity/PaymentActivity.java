package com.example.hotel_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.PaymentApiService;
import com.example.hotel_project.model.PaymentDTO;
import com.example.hotel_project.model.ResponseObject;
import com.example.hotel_project.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;

public class PaymentActivity extends AppCompatActivity {

    private Button btnPayment, btnComplete;
    private RadioButton radioCod, radioVnpay;
    private PaymentApiService paymentApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Xử lý intent khi mở activity
        handleIntent(getIntent());

        // Ánh xạ các view
        btnPayment = findViewById(R.id.btn_payment);
        btnComplete = findViewById(R.id.btn_checkout);
        radioCod = findViewById(R.id.radio_cod);
        radioVnpay = findViewById(R.id.radio_vnpay);

        // Khởi tạo Retrofit và PaymentApiService
        paymentApiService = RetrofitClient.getRetrofit().create(PaymentApiService.class);


        // Khi người dùng nhấn nút "Payment"
        btnPayment.setOnClickListener(v -> {
            if (radioVnpay.isChecked()) {
                // Tạo đối tượng PaymentDTO
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setAmount(120000);  // Ví dụ giá trị, thay bằng giá trị thực tế
                paymentDTO.setOrderDescription("Hotel booking payment");



                // Gửi yêu cầu tạo URL thanh toán
                paymentApiService.createVNPayUrl(paymentDTO).enqueue(new Callback<ResponseObject<String>>() {
                    @Override
                    public void onResponse(Call<ResponseObject<String>> call, Response<ResponseObject<String>> response) {
                        if (response.isSuccessful()) {
                            String paymentUrl = response.body().getData();
                            if (paymentUrl != null) {
                                // Mở trình duyệt web để thực hiện thanh toán
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
                                startActivity(intent);
                            } else {
                                Toast.makeText(PaymentActivity.this, "Lỗi tạo URL thanh toán", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PaymentActivity.this, "Lỗi kết nối với API", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseObject<String>> call, Throwable t) {
                        Toast.makeText(PaymentActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (radioCod.isChecked()) {
                // Nếu chọn COD, không cần làm gì, thông báo là thanh toán khi nhận hàng
                Toast.makeText(PaymentActivity.this, "Đã nhận xong tiền mặt", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PaymentActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            }
        });

        // Khi người dùng nhấn nút "Complete"
        btnComplete.setOnClickListener(v -> {
            // Sau khi thanh toán thành công, thông báo đơn hàng hoàn tất
            Toast.makeText(PaymentActivity.this, "Đơn hàng hoàn tất!", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc activity hoặc chuyển về màn hình chính
        });

        // Kiểm tra kết quả thanh toán từ VNPAY sau khi người dùng quay lại ứng dụng
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null && "yourapp".equals(data.getScheme()) && "payment".equals(data.getHost())) {
            String txnRef = data.getQueryParameter("txnRef");
            String responseCode = data.getQueryParameter("responseCode");

            if ("00".equals(responseCode)) {
                // Thanh toán thành công
                Toast.makeText(this, "Thanh toán thành công cho đơn hàng " + txnRef, Toast.LENGTH_LONG).show();
            } else {
                // Thanh toán thất bại
                Toast.makeText(this, "Thanh toán thất bại (code: " + responseCode + ")", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Xử lý intent khi activity đã chạy rồi
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null && "yourapp".equals(data.getScheme()) && "payment".equals(data.getHost())) {
            String txnRef = data.getQueryParameter("txnRef");
            String responseCode = data.getQueryParameter("responseCode");

            if (responseCode != null) {
                if ("00".equals(responseCode)) {
                    showPaymentSuccess(txnRef);
                } else {
                    showPaymentFailure(responseCode);
                }
            } else if (data.getQueryParameter("error") != null) {
                showPaymentError(data.getQueryParameter("error"));
            }
        }
    }

    private void showPaymentSuccess(String txnRef) {
        runOnUiThread(() -> {
            Toast.makeText(this, "Thanh toán thành công - Cảm ơn quý khách " , Toast.LENGTH_LONG).show();
            // Cập nhật UI tại đây
            btnComplete.setEnabled(true);
        });
    }

    private void showPaymentFailure(String errorCode) {
        runOnUiThread(() -> {
            Toast.makeText(this, "Thanh toán thất bại. Mã lỗi: " + errorCode, Toast.LENGTH_LONG).show();
        });
    }

    private void showPaymentError(String error) {
        runOnUiThread(() -> {
            Toast.makeText(this, "Lỗi xác thực: " + error, Toast.LENGTH_LONG).show();
        });
    }
}

