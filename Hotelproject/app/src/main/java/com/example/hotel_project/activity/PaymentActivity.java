package com.example.hotel_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.api.BookingScheduleService;
import com.example.hotel_project.api.PaymentApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.PaymentDTO;
import com.example.hotel_project.model.ResponseObject;
import com.example.hotel_project.model.RoomDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;
import java.text.ParseException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private Button btnPayment, btnComplete;

    private TextView amount_price;
    private RadioButton radioCod, radioVnpay;
    private PaymentApiService paymentApiService;

    private AccountDTO accountDTO;
    private GuestDTO guestDTO;

    private RoomDTO roomDTO;

    private Hotel hotel;

    private int totalPrice;

    private String dateStart, dateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Xử lý intent khi mở activity
        handleIntent(getIntent());

        // Ánh xạ các view
        Intent intent = getIntent();
        totalPrice = intent.getIntExtra("totalPrice", 0);
        guestDTO = SharedPreferencesManager.getGuestDTO(this);
        accountDTO = SharedPreferencesManager.getAccountDTO(this);
        roomDTO = (RoomDTO) intent.getSerializableExtra("roomDTO");
        hotel  = (Hotel) intent.getSerializableExtra("hotel");
        String checkIn = getIntent().getStringExtra("checkIn");
        String checkOut = getIntent().getStringExtra("checkOut");

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date checkInDate = inputFormat.parse(checkIn);
            Date checkOutDate = inputFormat.parse(checkOut);
            dateStart = isoFormat.format(checkInDate);
            dateEnd = isoFormat.format(checkOutDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi định dạng ngày khi chuyển đổi!", Toast.LENGTH_SHORT).show();
        }

        btnPayment = findViewById(R.id.btn_payment);
        btnComplete = findViewById(R.id.btn_checkout);
        radioCod = findViewById(R.id.radio_cod);
        radioVnpay = findViewById(R.id.radio_vnpay);
        amount_price = findViewById(R.id.amount_price);

        amount_price.setText(String.valueOf(totalPrice) + "đ");

        paymentApiService = RetrofitClient.getRetrofit().create(PaymentApiService.class);
        // Khi người dùng nhấn nút "Payment"
        btnPayment.setOnClickListener(v -> {
            if (radioVnpay.isChecked()) {
                // Tạo đối tượng PaymentDTO
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setAmount(totalPrice);  // Ví dụ giá trị, thay bằng giá trị thực tế
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
        btnComplete.setOnClickListener(v -> {
            String accountId = accountDTO.getId();
            String roomId = roomDTO.getId();
            String hotelId = hotel.getId();
            BigDecimal total = new BigDecimal(totalPrice);
            Toast.makeText(PaymentActivity.this, "Đơn hàng hoàn tất!", Toast.LENGTH_SHORT).show();
            BookingScheduleService apiService = RetrofitClient.getRetrofit().create(BookingScheduleService.class);
            Call<Boolean> call = apiService.createBooking(accountId, roomId, hotelId, dateStart, dateEnd, total);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body()) {
                            Toast.makeText(PaymentActivity.this, "Tạo booking thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PaymentActivity.this, "Tạo booking thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PaymentActivity.this, "Lỗi server!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(PaymentActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        });

        // Kiểm tra kết quả thanh toán từ VNPAY sau khi người dùng quay lại ứng dụng
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

