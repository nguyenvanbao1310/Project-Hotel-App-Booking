package com.example.hotel_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hotel_project.R;
import com.example.hotel_project.activity.MyInformationActivity;
import com.example.hotel_project.activity.MyReviewActivity;
import com.example.hotel_project.adapter.ProfileMenuAdapter;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.ProfileMenuItem;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private ListView listView;
    private TextView textName, textEmail;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        listView = view.findViewById(R.id.profileMenuList);

        GuestDTO guestDTO = SharedPreferencesManager.getGuestDTO(getActivity());
        AccountDTO accountDTO = SharedPreferencesManager.getAccountDTO(getActivity());

        textName = view.findViewById(R.id.txtName);
        textEmail = view.findViewById(R.id.txtEmail);

        textName.setText(guestDTO.getFullname());
        textEmail.setText(accountDTO.getEmail());

        List<ProfileMenuItem> items = new ArrayList<>();
        items.add(new ProfileMenuItem(R.drawable.ic_booking, "My Booking"));
        items.add(new ProfileMenuItem(R.drawable.ic_history, "History"));
        items.add(new ProfileMenuItem(R.drawable.ic_payment, "Payment Method"));
        items.add(new ProfileMenuItem(R.drawable.ic_information, "My Information"));
        items.add(new ProfileMenuItem(R.drawable.ic_review, "My Reviews"));
        items.add(new ProfileMenuItem(R.drawable.ic_setting, "Setting"));

        ProfileMenuAdapter adapter = new ProfileMenuAdapter(getContext(), items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            ProfileMenuItem item = (ProfileMenuItem) parent.getItemAtPosition(position);
            if ("My Information".equals(item.getTitle())) {
                // Chuyển sang MyInformationActivity
                Intent intent = new Intent(getActivity(), MyInformationActivity.class);
                startActivity(intent);
            }

            if ("My Reviews".equals(item.getTitle())) {
                Intent intent = new Intent(getActivity(), MyReviewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
