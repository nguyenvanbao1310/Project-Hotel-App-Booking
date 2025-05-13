package com.example.hotel_project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hotel_project.fragment.AboutFragment;
import com.example.hotel_project.fragment.ReviewFragment;
import com.example.hotel_project.fragment.TypeFragment;
import com.example.hotel_project.model.Hotel;

public class TabHotelDetailAdaper extends FragmentStateAdapter {

    private final Hotel hotel;
    public TabHotelDetailAdaper(@NonNull FragmentActivity fragmentActivity, Hotel hotel) {
        super(fragmentActivity);
        this.hotel = hotel;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return AboutFragment.newInstance(hotel);
            case 1: return TypeFragment.newInstance(hotel);
            case 2: return ReviewFragment.newInstance(hotel);
            default: return new AboutFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // About, Type, Review
    }
}
