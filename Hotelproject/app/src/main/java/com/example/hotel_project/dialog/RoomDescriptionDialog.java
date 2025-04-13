package com.example.hotel_project.dialog;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.ExtensionAdapter;
import com.example.hotel_project.adapter.ImageSliderAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class RoomDescriptionDialog extends BottomSheetDialogFragment {
    private List<String> imageUrls;
    private List<String> extension;

    private Handler handler = new Handler();
    private int currentIndex = 0;
    private Runnable sliderRunnable;

    public RoomDescriptionDialog(List<String> imageUrls, List<String> extension) {
        this.imageUrls = imageUrls;
        this.extension = extension;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_room_description, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.viewPagerImages);
        ImageSliderAdapter adapter = new ImageSliderAdapter(imageUrls);
        viewPager.setAdapter(adapter);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewExtensions);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        ExtensionAdapter extensionAdapter = new ExtensionAdapter(extension);
        recyclerView.setAdapter(extensionAdapter);


        // Auto slide sau mỗi 2 giây
        sliderRunnable = () -> {
            if (viewPager.getAdapter() != null) {
                currentIndex = (currentIndex + 1) % imageUrls.size();
                viewPager.setCurrentItem(currentIndex, true);
                handler.postDelayed(sliderRunnable, 2000);
            }
        };
        handler.postDelayed(sliderRunnable, 2000);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(sliderRunnable);
    }
}
