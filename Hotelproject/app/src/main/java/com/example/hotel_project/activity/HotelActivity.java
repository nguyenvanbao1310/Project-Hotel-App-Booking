package com.example.hotel_project.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.adapter.HotelAdapter;
import com.example.hotel_project.adapter.NearbyHotelAdapter;
import com.example.hotel_project.api.HotelApiService;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.R;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private RecyclerView recyclerViewNearby;

    private HotelAdapter adapter;

    private NearbyHotelAdapter nearbyHotelAdapter;

    private List<Hotel> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        recyclerView = findViewById(R.id.recyclerViewHotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewNearby = findViewById(R.id.recyclerViewHotelsNearby);
        recyclerViewNearby.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // Khởi tạo Retrofit và gọi API để lấy dữ liệu khách sạn
        HotelApiService apiService = RetrofitClient.getRetrofit().create(HotelApiService.class);
        Call<List<Hotel>> call = apiService.getHotels();

        // Gọi API và nhận phản hồi
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    hotelList = response.body();
                    adapter = new HotelAdapter(HotelActivity.this, hotelList);
                    nearbyHotelAdapter = new NearbyHotelAdapter(HotelActivity.this, hotelList);
                    recyclerView.setAdapter(adapter);
                    recyclerViewNearby.setAdapter(nearbyHotelAdapter);
                } else {
                    Toast.makeText(HotelActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Toast.makeText(HotelActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                Log.e("HotelActivity", "Error: " + t.getMessage());
            }
        });
    }
}
