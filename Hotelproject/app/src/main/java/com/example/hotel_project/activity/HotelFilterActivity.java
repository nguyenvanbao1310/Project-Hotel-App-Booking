package com.example.hotel_project.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.HotelAdapter;
import com.example.hotel_project.adapter.NearbyHotelAdapter;
import com.example.hotel_project.api.HotelApiService;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelFilterActivity  extends AppCompatActivity {
    private NearbyHotelAdapter nearbyHotelAdapter;

    private RecyclerView recyclerViewNearby;

    private List<Hotel> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_filter);

        int priceMin = getIntent().getIntExtra("priceMin", 0);
        int priceMax = getIntent().getIntExtra("priceMax", 0);

        Log.d("price", "priceMin: " + priceMin + priceMax );
        float rating = getIntent().getFloatExtra("rating", 0);

        recyclerViewNearby = findViewById(R.id.list_hotel_filter);
        recyclerViewNearby.setLayoutManager(new LinearLayoutManager(HotelFilterActivity.this, LinearLayoutManager.VERTICAL, false));

        HotelApiService apiService = RetrofitClient.getRetrofit().create(HotelApiService.class);
        Call<List<Hotel>> call = apiService.findHotelsByPriceRangeAndRating(priceMin, priceMax, rating);
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    Log.d("HotelData", "API response successful");
                    hotelList = response.body();
                    for (Hotel hotel : hotelList) {
                        Log.d("HotelData", "Hotel: " + hotel.getName() + ", Image: " + hotel.getHotel_image_url());
                    }
                    nearbyHotelAdapter = new NearbyHotelAdapter(HotelFilterActivity.this, hotelList);
                    recyclerViewNearby.setAdapter(nearbyHotelAdapter);
                } else {
                    Toast.makeText(HotelFilterActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Toast.makeText(HotelFilterActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                Log.e("HotelFilterActivity", "Error: " + t.getMessage());
            }
        });

    }
}
