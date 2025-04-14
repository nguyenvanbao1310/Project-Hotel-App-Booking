package com.example.hotel_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.ReviewAdapter;
import com.example.hotel_project.api.ReviewApiService;
import com.example.hotel_project.api.RoomApiService;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.model.RoomDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private List<ReviewDTO> reviewList;

    private TextView averageRatingText, totalRatingText;

    private RatingBar ratingBar;

    private ProgressBar progressBar1Star, progressBar2Star, progressBar3Star,
            progressBar4Star, progressBar5Star;

    private Hotel hotel;

    public ReviewFragment() {}

    public static ReviewFragment newInstance(Hotel hotel) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable("hotel", hotel); // Truyền hotel qua Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hotel = (Hotel) getArguments().getSerializable("hotel");
        }
        Log.d("ReviewFragment", "hotel = " + hotel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView = rootView.findViewById(R.id.review_list);
        averageRatingText = rootView.findViewById(R.id.average_rating);
        totalRatingText = rootView.findViewById(R.id.review_count);
        ratingBar = rootView.findViewById(R.id.rating_bar);
        progressBar1Star = rootView.findViewById(R.id.progressBar1Star);
        progressBar2Star = rootView.findViewById(R.id.progressBar2Star);
        progressBar3Star = rootView.findViewById(R.id.progressBar3Star);
        progressBar4Star = rootView.findViewById(R.id.progressBar4Star);
        progressBar5Star = rootView.findViewById(R.id.progressBar5Star);
        reviewList = new ArrayList<>();

        reviewAdapter = new ReviewAdapter(this,reviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(reviewAdapter);

        fetchRooms();

        return rootView;
    }

    private void fetchRooms() {
        ReviewApiService apiService = RetrofitClient.getRetrofit().create(ReviewApiService.class);
        Call<List<ReviewDTO>> call = apiService.getReviewByHotelId(hotel.getId());
        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                if (response.isSuccessful()) {
                    reviewList.clear();
                    reviewList.addAll(response.body());
                    reviewAdapter.notifyDataSetChanged();
                    loadRatings(reviewList);
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

    private void loadRatings(List<ReviewDTO> reviewList)
    {
        int[] ratingCounts = new int[5];
        float totalRating = 0;
        for (ReviewDTO review : reviewList) {
            int rating = review.getRating();
            if (rating >= 1 && rating <= 5) {
                totalRating += rating;
                ratingCounts[rating - 1]++;
            }
        }
        int totalReviews = reviewList.size();
        float averageRating = totalRating / totalReviews;
        double[] ratingPercentages = new double[5];
        for (int i = 0; i < ratingCounts.length; i++) {
            ratingPercentages[i] = ((double) ratingCounts[i] / totalReviews) * 100;
        }

        String averageText = String.format(Locale.getDefault(), "%.1f", averageRating);
        averageRatingText.setText(averageText);
        ratingBar.setRating(averageRating);
        totalRatingText.setText(String.format("Based on %d reviews", totalReviews));
        progressBar1Star.setProgress((int) ratingPercentages[0]);
        progressBar2Star.setProgress((int) ratingPercentages[1]);
        progressBar3Star.setProgress((int) ratingPercentages[2]);
        progressBar4Star.setProgress((int) ratingPercentages[3]);
        progressBar5Star.setProgress((int) ratingPercentages[4]);
    }


}