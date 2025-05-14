package com.example.hotel_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.activity.HotelDetailActivity;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.List;

public class NearbyHotelAdapter extends RecyclerView.Adapter<NearbyHotelAdapter.HotelViewHolder>{
    private List<Hotel> hotelList;
    private Context context;

    public NearbyHotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public NearbyHotelAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_nearby, parent, false);
        return new NearbyHotelAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyHotelAdapter.HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.textName.setText(hotel.getName());
        holder.textAddress.setText(hotel.getAddress() + ", " + hotel.getCity());

        boolean isFavorite = isFavorite(hotel.getId());

        if (isFavorite) {
            holder.heartIcon.setImageResource(R.drawable.ic_heart_filled);  // Trái tim đầy
        } else {
            holder.heartIcon.setImageResource(R.drawable.ic_heart);  // Trái tim rỗng
        }

        // Load ảnh từ URL
        String fullUrl = RetrofitClient.IMG_URL
                + hotel.getHotel_image_url()
                + "?v=" + System.currentTimeMillis();
        Glide.with(context)
                .load(fullUrl)
                .into(holder.imageHotel);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetailActivity.class);
            intent.putExtra("hotel_id", hotel.getId());
            context.startActivity(intent);
        });

        holder.heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean currentFavoriteStatus = isFavorite(hotel.getId());

                boolean newFavoriteStatus = !currentFavoriteStatus;

                saveFavoriteStatus(hotel.getId(), newFavoriteStatus);

                if (newFavoriteStatus) {
                    holder.heartIcon.setImageResource(R.drawable.ic_heart_filled);
                } else {
                    holder.heartIcon.setImageResource(R.drawable.ic_heart);
                }

                Toast.makeText(context, newFavoriteStatus ? "Đã thêm vào yêu thích!" : "Đã xóa khỏi yêu thích!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    private boolean isFavorite(String hotelId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isFavorite_" + hotelId, false);
    }

    private void saveFavoriteStatus(String hotelId, boolean isFavorite) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFavorite_" + hotelId, isFavorite);
        editor.apply();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHotel;
        TextView textName, textAddress, textDescription;

        ImageView heartIcon;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHotel = itemView.findViewById(R.id.imageHotel);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
            heartIcon = itemView.findViewById(R.id.heartIcon);
        }
    }
}
