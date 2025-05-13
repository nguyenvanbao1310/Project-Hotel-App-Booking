package com.example.hotel_project.fragment;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.hotel_project.R;

public class FilterFragment extends Fragment {

    private RatingBar ratingBar;
    private Button lastSelectedButton = null;

    private int priceMin, priceMax;
    private float selectedRating = 0;

    private Button btn1, btn2, btn3, btn4, btn5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_layout, container, false);
        ratingBar = view.findViewById(R.id.rating_filter);
        btn1 = view.findViewById(R.id.price_button_1);
        btn2 = view.findViewById(R.id.price_button_2);
        btn3 = view.findViewById(R.id.price_button_3);
        btn4 = view.findViewById(R.id.price_button_4);
        btn5 = view.findViewById(R.id.price_button_5);

        btn1.setOnClickListener(this::onPriceButtonClick);
        btn2.setOnClickListener(this::onPriceButtonClick);
        btn3.setOnClickListener(this::onPriceButtonClick);
        btn4.setOnClickListener(this::onPriceButtonClick);
        btn5.setOnClickListener(this::onPriceButtonClick);

        // Chỉnh sửa màu của các sao trên RatingBar
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(android.graphics.Color.parseColor("#FFEB3B"), android.graphics.PorterDuff.Mode.SRC_ATOP); // đã chọn
        stars.getDrawable(1).setColorFilter(android.graphics.Color.parseColor("#FFECB3"), android.graphics.PorterDuff.Mode.SRC_ATOP); // 1 phần
        stars.getDrawable(0).setColorFilter(android.graphics.Color.LTGRAY, android.graphics.PorterDuff.Mode.SRC_ATOP);

        return view;
    }

    public void onPriceButtonClick(View view) {
        Button clickedButton = (Button) view;

        clickedButton.setTextColor(getResources().getColor(R.color.yellow));

        resetOtherButtons(clickedButton);

        if (clickedButton.getId() == R.id.price_button_1) {
            priceMin = 0;
            priceMax = 200000;
        } else if (clickedButton.getId() == R.id.price_button_2) {
            priceMin = 200000;
            priceMax = 400000;
        } else if (clickedButton.getId() == R.id.price_button_3) {
            priceMin = 400000;
            priceMax = 600000;
        } else if (clickedButton.getId() == R.id.price_button_4) {
            priceMin = 600000;
            priceMax = 800000;
        } else if (clickedButton.getId() == R.id.price_button_5) {
            priceMin = 800000;
            priceMax = 1000000;
        } else {
            priceMin = 0;
            priceMax = 0;
        }

    }

    private void resetOtherButtons(Button selectedButton) {
        Button[] buttons = new Button[]{
                btn1, btn2, btn3, btn4, btn5
        };

        for (Button button : buttons) {
            if (button != selectedButton) {
                button.setTextColor(getResources().getColor(R.color.text_primary));  // Màu chữ mặc định
            }
        }
    }
    public int getPriceMin() {
        return priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public float getSelectedRating() {
        return ratingBar.getRating();  // Giả sử bạn lấy giá trị rating từ RatingBar
    }






}


