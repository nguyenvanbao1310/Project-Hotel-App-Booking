package com.example.hotel_project.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.adapter.TabHotelDetailAdaper;
import com.example.hotel_project.adapter.ThumbnailAdapter;
import com.example.hotel_project.api.HotelApiService;
import com.example.hotel_project.interfaces.ImageClickListener;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelDetailActivity extends AppCompatActivity implements ImageClickListener {

    private ImageView mainImage;

    private TextView nameText ;
    private TextView addressText;

    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotel);

        String hotelId = getIntent().getStringExtra("hotel_id");

        nameText = findViewById(R.id.hotelName);
        addressText = findViewById(R.id.hotelAddress);



        if (hotelId != null) {
            getHotelDetail(hotelId); // Gọi API để lấy chi tiết khách sạn
        } else {
            Toast.makeText(this, "Hotel ID not provided", Toast.LENGTH_SHORT).show();
        }



    }

    private void getHotelDetail(String hotelId) {
        HotelApiService apiService = RetrofitClient.getRetrofit().create(HotelApiService.class);
        Call<Hotel> call = apiService.getHotelById(hotelId);

        call.enqueue(new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hotel = response.body();
                    Log.d("API Response", "Hotel data: " + hotel.toString());
                    showHotelDetail(hotel);

                    TabLayout tabLayout = findViewById(R.id.tabLayout);
                    ViewPager2 viewPager = findViewById(R.id.viewPager);
                    TabHotelDetailAdaper adapter = new TabHotelDetailAdaper(HotelDetailActivity.this, hotel);
                    viewPager.setAdapter(adapter);
                    new TabLayoutMediator(tabLayout, viewPager,
                            (tab, position) -> {
                                switch (position) {
                                    case 0: tab.setText("About"); break;
                                    case 1: tab.setText("Type"); break;
                                    case 2: tab.setText("Review"); break;
                                }
                            }).attach();

                } else {
                    Log.d("API Response", "Error: " + response.code());
                    Toast.makeText(HotelDetailActivity.this, "Failed to load hotel details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {
                Log.e("API Error", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(HotelDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHotelDetail(Hotel hotel) {
        List<String> images = hotel.getHotels_images();
        if (images == null || images.isEmpty()) {
            images = new ArrayList<>(); // Tránh lỗi NullPointerException
        }

        mainImage = findViewById(R.id.mainImage);
        RecyclerView recyclerThumbnails = findViewById(R.id.recyclerThumbnails);

        // Load ảnh chính ban đầu
        String mainImageUrl = RetrofitClient.IMG_URL + hotel.getHotel_image_url();
        Glide.with(this)
                .load(mainImageUrl)
                .into(mainImage);

        // Thiết lập RecyclerView nằm ngang
        recyclerThumbnails.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        // Gán adapter cho ảnh nhỏ
        ThumbnailAdapter adapter = new ThumbnailAdapter(images, this, imageUrl -> {
            updateMainImage(imageUrl);
        }, 0);

        recyclerThumbnails.setAdapter(adapter);
        nameText.setText(hotel.getName());
        addressText.setText(hotel.getAddress());
    }

    @Override
    public void onImageClick(String imageUrl) {
        // Khi người dùng nhấn vào ảnh, tải ảnh vào ImageView làm nền
        updateMainImage(imageUrl);
    }

    private void updateMainImage(String imageUrl) {
        Log.d("Image URL", imageUrl);
        // Sử dụng Glide để tải ảnh vào ImageView làm nền
        Glide.with(this).load(imageUrl).into(mainImage);
    }

}
