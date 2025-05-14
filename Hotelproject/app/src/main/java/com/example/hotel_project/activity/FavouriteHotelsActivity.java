package com.example.hotel_project.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteHotelsActivity extends AppCompatActivity {
    private NearbyHotelAdapter nearbyHotelAdapter;

    private RecyclerView recyclerViewNearby;

    private List<Hotel> favouriteHotels = new ArrayList<>();

    private HotelApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_filter);


        recyclerViewNearby = findViewById(R.id.list_hotel_filter);
        recyclerViewNearby.setLayoutManager(new LinearLayoutManager(FavouriteHotelsActivity.this, LinearLayoutManager.VERTICAL, false));
        apiService = RetrofitClient.getRetrofit().create(HotelApiService.class);
        loadFavouriteHotels();
    }
    private void loadFavouriteHotels() {
        SharedPreferences prefs = getSharedPreferences("Favorites", MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        List<String> favoriteHotelIds = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("isFavorite_") && Boolean.TRUE.equals(entry.getValue())) {
                String hotelId = entry.getKey().replace("isFavorite_", "");
                favoriteHotelIds.add(hotelId);
            }
        }
        if (favoriteHotelIds.isEmpty()) {
            Toast.makeText(this, "Không có khách sạn yêu thích", Toast.LENGTH_SHORT).show();
            return;
        }

        for (String hotelId : favoriteHotelIds) {
            apiService.getHotelById(hotelId).enqueue(new Callback<Hotel>() {
                @Override
                public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        favouriteHotels.add(response.body());
                        updateRecyclerView();
                    }
                }
                @Override
                public void onFailure(Call<Hotel> call, Throwable t) {
                    Toast.makeText(FavouriteHotelsActivity.this, "Lỗi tải khách sạn: " + hotelId, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void updateRecyclerView() {
        if (nearbyHotelAdapter == null) {
            nearbyHotelAdapter = new NearbyHotelAdapter(FavouriteHotelsActivity.this, favouriteHotels);
            recyclerViewNearby.setAdapter(nearbyHotelAdapter);
        } else {
            nearbyHotelAdapter.notifyDataSetChanged();
        }
    }
}
