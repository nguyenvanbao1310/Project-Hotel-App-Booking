package com.example.hotel_project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.interfaces.ImageClickListener;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.Collections;
import java.util.List;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder> {
    private List<String> imageList;
    private Context context;
    private ImageClickListener imageClickListener;
    private int selectedPosition;

    // Constructor
    public ThumbnailAdapter(List<String> imageList, Context context, ImageClickListener imageClickListener, int selectedPosition) {
        this.imageList = imageList != null ? imageList : Collections.emptyList(); // tránh null
        this.context = context;
        this.imageClickListener = imageClickListener;
        this.selectedPosition = selectedPosition; // chọn ảnh mặc định khi khởi tạo
    }

    @NonNull
    @Override
    public ThumbnailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thumbnail, parent, false);
        return new ThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThumbnailViewHolder holder, int position) {
        String imageUrl = RetrofitClient.IMG_URL + imageList.get(position);

        // Log khi bind ảnh vào ViewHolder
        Log.d("ThumbnailAdapter", "Binding position: " + position);
        Log.d("ThumbnailAdapter", "Current selected position: " + selectedPosition);

        Glide.with(context)
                .load(imageUrl)
                .into(holder.thumbnailImage);

        // Đặt viền tùy theo ảnh được chọn
        if (selectedPosition == position) {
            holder.thumbnailImage.setBackgroundResource(R.drawable.thumbnail_selected_border);
        } else {
            holder.thumbnailImage.setBackgroundResource(R.drawable.thumbnail_border);
        }

        // Bắt sự kiện click
        holder.thumbnailImage.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            Log.d("ThumbnailAdapter", "Position clicked: " + currentPosition);

            // Kiểm tra nếu có thay đổi vị trí đã chọn
            if (selectedPosition != currentPosition) {
                int previousSelected = selectedPosition;
                selectedPosition = currentPosition;

                // Log thêm thông tin về sự thay đổi của selectedPosition
                Log.d("ThumbnailAdapter", "Selected position changed: " + selectedPosition);

                // Cập nhật ảnh cũ và mới
                if (previousSelected != -1) {
                    notifyItemChanged(previousSelected);  // Cập nhật ảnh trước đó
                }
                notifyItemChanged(selectedPosition);  // Cập nhật ảnh mới

                // Gọi hàm callback khi chọn ảnh
                imageClickListener.onImageClick(RetrofitClient.IMG_URL + imageList.get(currentPosition));
                Log.d("ThumbnailAdapter", "Selected image: " + imageList.get(currentPosition));
            } else {
                Log.d("ThumbnailAdapter", "No change in selection");
            }
        });
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ThumbnailViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImage;

        public ThumbnailViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImage = itemView.findViewById(R.id.thumbnailImage);
        }
    }
}
