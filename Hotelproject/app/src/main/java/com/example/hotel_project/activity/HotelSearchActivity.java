package com.example.hotel_project.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.NearbyHotelAdapter;
import com.example.hotel_project.model.Hotel;

import java.util.List;

public class HotelSearchActivity extends AppCompatActivity {
    private NearbyHotelAdapter nearbyHotelAdapter;

    private RecyclerView recyclerViewNearby;

    private List<Hotel> hotelList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_filter);
        hotelList = (List<Hotel>) getIntent().getSerializableExtra("hotelList");

        recyclerViewNearby = findViewById(R.id.list_hotel_filter);
        recyclerViewNearby.setLayoutManager(new LinearLayoutManager(HotelSearchActivity.this, LinearLayoutManager.VERTICAL, false));

        nearbyHotelAdapter = new NearbyHotelAdapter(HotelSearchActivity.this, hotelList);
        recyclerViewNearby.setAdapter(nearbyHotelAdapter);


    }

}
