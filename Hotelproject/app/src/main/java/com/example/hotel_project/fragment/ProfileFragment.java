package com.example.hotel_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.ProfileMenuAdapter;
import com.example.hotel_project.model.ProfileMenuItem;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private ListView listView;
    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        listView = view.findViewById(R.id.profileMenuList);
        List<ProfileMenuItem> items = new ArrayList<>();
        items.add(new ProfileMenuItem(R.drawable.ic_booking, "My Booking"));
        items.add(new ProfileMenuItem(R.drawable.ic_history, "History"));
        items.add(new ProfileMenuItem(R.drawable.ic_payment, "Payment Method"));
        items.add(new ProfileMenuItem(R.drawable.ic_information, "My Information"));
        items.add(new ProfileMenuItem(R.drawable.ic_review, "My Reviews"));
        items.add(new ProfileMenuItem(R.drawable.ic_setting, "Setting"));

        ProfileMenuAdapter adapter = new ProfileMenuAdapter(getContext(), items);
        listView.setAdapter(adapter);
        return view;
    }

}
