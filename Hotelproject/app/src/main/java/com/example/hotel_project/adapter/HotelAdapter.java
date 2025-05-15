package com.example.hotel_project.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.activity.HotelDetailActivity;
import com.example.hotel_project.api.ReviewApiService;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.R;
import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        holder.textRating.setText("");
        fetchAndShowAverageRating(hotel.getId(), holder.textRating);

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

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 0.7f, 1.2f, 1.0f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 0.7f, 1.2f, 1.0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(300);
                animatorSet.playTogether(scaleX, scaleY);
                animatorSet.start();

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

    private void fetchAndShowAverageRating(String hotelId, TextView ratingTextView)
    {
        ReviewApiService apiService = RetrofitClient.getRetrofit().create(ReviewApiService.class);
        Call<List<ReviewDTO>> call = apiService.getReviewByHotelId(hotelId);
        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                if (response.isSuccessful()) {
                    List<ReviewDTO> reviews = response.body();
                    double avgRating = calculateAverageRating(reviews);
                    ratingTextView.setText(String.format("%.1f", avgRating));
                } else {
                    Log.e("ReviewFragment", "Error: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {
                Log.e("ReviewFragment", "API Failure: ", t);
            }
        });
    }
    private double calculateAverageRating(List<ReviewDTO> reviews) {
        if (reviews == null || reviews.isEmpty()) return 5.0; // Nếu không có review thì là 5 sao

        double sum = 0;
        for (ReviewDTO r : reviews) {
            sum += r.getRating();
        }
        double avg = sum / reviews.size();

        if (avg == 0) return 5.0;  // Nếu trung bình là 0 thì đổi thành 5

        return avg;
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHotel;
        TextView textName, textAddress, textDescription, textRating;
        ImageView heartIcon;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHotel = itemView.findViewById(R.id.imageHotel);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
            heartIcon = itemView.findViewById(R.id.heartIcon);
            textRating = itemView.findViewById(R.id.textRating);
        }
    }
}

