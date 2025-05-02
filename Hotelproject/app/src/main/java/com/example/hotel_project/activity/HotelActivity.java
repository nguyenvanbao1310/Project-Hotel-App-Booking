package com.example.hotel_project.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.hotel_project.R;
import com.example.hotel_project.fragment.HomeFragment;
import com.example.hotel_project.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HotelActivity extends AppCompatActivity {

    private LinearLayout tabHome, tabExplore, tabFavourite, tabChat, tabProfile;
    private TextView tabHomeText, tabProfileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        tabHome = findViewById(R.id.tabHome);
        tabExplore = findViewById(R.id.tabExplore);
        tabFavourite = findViewById(R.id.tabFavourite);
        tabChat = findViewById(R.id.tabChat);
        tabProfile = findViewById(R.id.tabProfile);

        tabHome.setOnClickListener(v -> {
            switchFragment(new HomeFragment());
        });
        tabProfile.setOnClickListener(v -> {
            switchFragment(new ProfileFragment());
        });

        if (savedInstanceState == null) {
            switchFragment(new HomeFragment());
        }

    }
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

}
