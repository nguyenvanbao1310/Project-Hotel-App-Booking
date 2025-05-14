package com.example.hotel_project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hotel_project.fragment.UpcomingBookingFragment;

public class BookingPagerAdapter extends FragmentStateAdapter {
    public BookingPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new UpcomingBookingFragment();
//            case 1: return new CompletedBookingFragment();
//            case 2: return new CancelledBookingFragment();
            default: return new UpcomingBookingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
