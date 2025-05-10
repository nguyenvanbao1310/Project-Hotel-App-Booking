package com.example.hotel_project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.activity.HotelDetailActivity;
import com.example.hotel_project.api.HotelApiService;
import com.example.hotel_project.api.ReviewApiService;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<ReviewDTO> reviewList;

    private List<String> reviewImages;

    private Hotel hotel;


    public MyReviewAdapter(Context context, List<ReviewDTO> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, img1, img2, img3, imgHotel;
        TextView txtName, txtDate,txtComment, txtRating, txtRateLocation, txtRateService,
                txtRatingValue, txtHotelName, txtHotelAddress, txtHotelRating;

        ProgressBar pbLocation, pbService;



        public ReviewViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            txtName = itemView.findViewById(R.id.tvUserName);
            txtDate = itemView.findViewById(R.id.tvDate);
            txtComment = itemView.findViewById(R.id.tvReviewContent);
            txtRating = itemView.findViewById(R.id.tvUserRating);
            txtRatingValue = itemView.findViewById(R.id.tvRatingValue);
            txtRateLocation = itemView.findViewById(R.id.tvLocation);
            txtRateService = itemView.findViewById(R.id.tvService);
            pbLocation = itemView.findViewById(R.id.pbLocation);
            pbService = itemView.findViewById(R.id.pbService);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            imgHotel = itemView.findViewById(R.id.imageHotel);
            txtHotelName = itemView.findViewById(R.id.tvHotelName);
            txtHotelAddress = itemView.findViewById(R.id.tvHotelAddress);
            txtHotelRating = itemView.findViewById(R.id.tvHotelRating);
        }
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_review, parent, false); // file XML bạn đã tạo
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewDTO review = reviewList.get(position);


        // Gán tên người dùng
        holder.txtName.setText(review.getUserName());

        // Gán comment
        holder.txtComment.setText(review.getContent());

        holder.txtDate.setText(review.getReviewDate().toString());

        holder.txtRateLocation.setText("Location " +review.getRateLocation());

        holder.txtRateService.setText("Service " +review.getRateService());

        int locationProgress = (int) ((review.getRateLocation() / 5.0) * 100);
        int serviceProgress = (int) ((review.getRateService() / 5.0) * 100);
        holder.pbLocation.setProgress(locationProgress);
        holder.pbService.setProgress(serviceProgress);

        // Gán rating
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < (int) review.getRating(); i++) {
            stars.append("★ ");
        }
        holder.txtRating.setText(stars.toString().trim());

        holder.txtRatingValue.setText(String.valueOf(review.getRating()));

        reviewImages = review.getImageReviews();

        ImageView[] imageViews = new ImageView[] {
                holder.img1, holder.img2, holder.img3
        };

        for (int i = 0; i < 3; i++) {
            if (reviewImages != null && i < reviewImages.size()) {
                imageViews[i].setVisibility(View.VISIBLE);
                Glide.with(holder.itemView.getContext()).load(RetrofitClient.IMG_URL+reviewImages.get(i)).into(imageViews[i]);
            } else {
                imageViews[i].setVisibility(View.GONE);
            }
        }

        HotelApiService apiService = RetrofitClient.getRetrofit().create(HotelApiService.class);
        Call<Hotel> call = apiService.getHotelById(review.getHotelId());  // bạn cần có API này
        call.enqueue(new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Hotel hotelRes = response.body();
                    holder.txtHotelName.setText(hotelRes.getName());
                    holder.txtHotelAddress.setText(hotelRes.getAddress());
                    Log.d("IMG_URL", "IMG_URL = " + RetrofitClient.IMG_URL +hotelRes.getHotel_image_url());
                    if (hotelRes.getHotel_image_url() != null && !hotelRes.getHotel_image_url().isEmpty()) {
                        Glide.with(holder.itemView.getContext())
                                .load(RetrofitClient.IMG_URL + hotelRes.getHotel_image_url())
                                .into(holder.imgHotel);
                    } else {
                        holder.imgHotel.setVisibility(View.GONE); // hoặc ảnh mặc định
                    }
                }
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {
                Log.e("HotelLoad", "Failed to load hotel info", t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


}
