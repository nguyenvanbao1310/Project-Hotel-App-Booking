package com.example.hotel_project.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.MyHistoryAdapter;
import com.example.hotel_project.api.BookingOrderService;
import com.example.hotel_project.model.BookingOrderDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MyHistoryAdapter myHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);

        recyclerView = findViewById(R.id.list_my_history);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        BookingOrderService api = RetrofitClient.getRetrofit().create(BookingOrderService.class);
        Call<List<BookingOrderDTO>> call = api.getBookingOrdersByAccountId("G001");

        call.enqueue(new Callback<List<BookingOrderDTO>>() {
            @Override
            public void onResponse(Call<List<BookingOrderDTO>> call, Response<List<BookingOrderDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookingOrderDTO> orders = response.body();
                    myHistoryAdapter = new MyHistoryAdapter(MyHistoryActivity.this, orders);
                    recyclerView.setAdapter(myHistoryAdapter);
                } else {
                    Toast.makeText(MyHistoryActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookingOrderDTO>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi gọi API: " + t.getMessage());
                Toast.makeText(MyHistoryActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
