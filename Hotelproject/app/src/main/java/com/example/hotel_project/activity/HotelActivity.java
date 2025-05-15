package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.hotel_project.R;
import com.example.hotel_project.enums.EnumRole;
import com.example.hotel_project.fragment.HomeFragment;
import com.example.hotel_project.fragment.ProfileFragment;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;


public class HotelActivity extends AppCompatActivity {

    private LinearLayout tabHome, tabExplore, tabFavourite, tabChat, tabProfile;
    private TextView tabHomeText, tabProfileText;

    private GuestDTO guestDTO;

    private AccountDTO accountDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

//        // set cố định Guest để chay, sau khi làm login thì có thể bỏ cái này.
//        getGuest();
//        getAccount();
//
//        SharedPreferencesManager.saveGuestDTO(this, guestDTO);
//        SharedPreferencesManager.saveAccountDTO(this, accountDTO);
        accountDTO = SharedPreferencesManager.getAccountDTO(this);
        guestDTO = SharedPreferencesManager.getGuestDTO(this); // Lấy GuestDTO

        if (accountDTO == null) {
            Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            Log.d("HotelActivity", "accountDTO is null");
            // Chuyển về màn hình đăng nhập nếu không có account
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        if (guestDTO == null) {
            Toast.makeText(this, "Chưa có thông tin khách", Toast.LENGTH_SHORT).show();
            Log.d("HotelActivity", "guestDTO is null");
            // Có thể yêu cầu người dùng đăng ký thêm thông tin khách hàng (nếu cần)
        } else {
            Toast.makeText(this, "Xin chào " + guestDTO.getFullname(), Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Xin chào " + accountDTO.getUsername(), Toast.LENGTH_SHORT).show();

        tabHome = findViewById(R.id.tabHome);
        tabExplore = findViewById(R.id.tabExplore);
        tabFavourite = findViewById(R.id.tabFavourite);
        tabChat = findViewById(R.id.tabChat);
        tabProfile = findViewById(R.id.tabProfile);

        tabHome.setOnClickListener(v -> {
            Fragment fragment = new HomeFragment();
            switchFragment(fragment);
        });
        tabProfile.setOnClickListener(v -> {
            Fragment fragment = new ProfileFragment();
            switchFragment(fragment);
        });
        tabExplore.setOnClickListener(v -> {
            Intent intent = new Intent(HotelActivity.this, MapsActivity.class);
            startActivity(intent);
        });
        tabFavourite.setOnClickListener(v -> {
            Intent intent = new Intent(HotelActivity.this, FavouriteHotelsActivity.class);
            startActivity(intent);
        });

        if (savedInstanceState == null) {
            Fragment fragment = new HomeFragment();
            switchFragment(fragment);
        }

    }
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

//    private void getGuest()
//    {
//        guestDTO = new GuestDTO();
//        guestDTO.setId("P003");
//        guestDTO.setFullname("Nguyễn Hoàng Gia Phong");
//        guestDTO.setCccd("051204020183");
//        guestDTO.setAddress("Thủ Đức, TP. Hồ Chí Minh");
//        guestDTO.setGender(true);
//        guestDTO.setAccount_id("G001");
//    }
//
//    private void getAccount(){
//        accountDTO = new AccountDTO();
//        accountDTO.setId("G001");
//        accountDTO.setUsername("giaphong1310");
//        accountDTO.setPassword("13102004bao");
//        accountDTO.setEmail("nguyenhoanggiaphong12@gmail.com");
//        accountDTO.setPhone("0379420232");
//        accountDTO.setRole(EnumRole.GUEST);
//
//    }
}
