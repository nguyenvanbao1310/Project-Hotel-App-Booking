package com.example.hotel_project.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.MyReviewAdapter;
import com.example.hotel_project.adapter.ReviewAdapter;
import com.example.hotel_project.api.HotelApiService;
import com.example.hotel_project.api.ReviewApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyReviewAdapter reviewAdapter;
    private List<ReviewDTO> reviewList;

    private GuestDTO guestDTO;
    private AccountDTO accountDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        recyclerView = findViewById(R.id.list_my_reiview);

        guestDTO = SharedPreferencesManager.getGuestDTO(this);
        accountDTO = SharedPreferencesManager.getAccountDTO(this);

        reviewList = new ArrayList<>();

        reviewAdapter = new MyReviewAdapter(MyReviewActivity.this, reviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewAdapter);
        fetchRooms();
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            finish();
        });

    }

    private void fetchRooms(){
        ReviewApiService apiService = RetrofitClient.getRetrofit().create(ReviewApiService.class);
        Call<List<ReviewDTO>> call = apiService.getReviewByAccountId(accountDTO.getId());
        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                if (response.isSuccessful()) {
                    reviewList.clear();
                    reviewList.addAll(response.body());
                    reviewAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ReviewFragment", "Error: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {
                Log.e("ReviewFragment", "API Failure: ", t);
            }
        });
    }

}
