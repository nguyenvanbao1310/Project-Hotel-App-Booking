package com.example.hotel_project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hotel_project.fragment.FilterFragment;
import com.example.hotel_project.fragment.SortByFragment;

public class FilterPagerAdapter extends FragmentStateAdapter {
    private FilterFragment filterFragment = new FilterFragment();
    private SortByFragment sortFragment = new SortByFragment();

    public FilterPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return filterFragment;
        else return sortFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public FilterFragment getFilterFragment() {
        return filterFragment;
    }

    public SortByFragment getSortFragment() {
        return sortFragment;
    }
}

