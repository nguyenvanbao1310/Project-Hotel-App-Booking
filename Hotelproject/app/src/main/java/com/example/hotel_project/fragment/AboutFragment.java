package com.example.hotel_project.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.model.Hotel;

public class AboutFragment extends Fragment {

    private TextView descriptionText, ownerName;
    private ImageView ownerImage;
    private ImageButton zaloButton, callButton;

    private Hotel currentHotel; // giả sử bạn truyền hotel này từ activity hoặc database

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(Hotel hotel) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putSerializable("hotel_data", hotel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentHotel = (Hotel) getArguments().getSerializable("hotel_data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Ánh xạ view
        descriptionText = view.findViewById(R.id.descriptionText);
        ownerName = view.findViewById(R.id.ownerName);
        ownerImage = view.findViewById(R.id.ownerImage);
        zaloButton = view.findViewById(R.id.zaloButton);
        callButton = view.findViewById(R.id.callButton);

        if (currentHotel != null) {
            descriptionText.setText(currentHotel.getDescription());
            ownerName.setText("Nguyễn Văn Bảo");
            ownerImage.setImageResource(R.drawable.ic_avatar_contact);

//            ownerName.setText(currentHotel.getOwnerName());
//
//            // Load ảnh bằng Glide
//            Glide.with(this)
//                    .load(currentHotel.getOwnerImageUrl())
//                    .placeholder(R.drawable.owner_placeholder)
//                    .into(ownerImage);
        }

        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(callButton, "rotation", -10f, 10f, -10f, 10f, 0f);

        ObjectAnimator shakeAnimator1 = ObjectAnimator.ofFloat(zaloButton, "rotation", -10f, 10f, -10f, 10f, 0f);

        shakeAnimator.setDuration(500); // Thời gian 1 lần rung
        shakeAnimator.setRepeatCount(ValueAnimator.INFINITE); // Lặp vô hạn
        shakeAnimator.setRepeatMode(ValueAnimator.RESTART);  // Bắt đầu lại từ đầu
        shakeAnimator.start();

        shakeAnimator1.setDuration(500); // Thời gian 1 lần rung
        shakeAnimator1.setRepeatCount(ValueAnimator.INFINITE); // Lặp vô hạn
        shakeAnimator1.setRepeatMode(ValueAnimator.RESTART);  // Bắt đầu lại từ đầu
        shakeAnimator1.start();

        zaloButton.setOnClickListener(v -> {
        });

        callButton.setOnClickListener(v -> {
        });

        return view;
    }
}
