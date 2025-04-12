package com.example.hotel_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.activity.HotelDetailActivity;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.R;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Retrofit;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotelList;
    private Context context;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.textName.setText(hotel.getName());
        holder.textAddress.setText(hotel.getAddress() + ", " + hotel.getCity());

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
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHotel;
        TextView textName, textAddress, textDescription;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHotel = itemView.findViewById(R.id.imageHotel);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
        }
    }
}

