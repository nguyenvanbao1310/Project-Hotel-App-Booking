package com.example.hotel_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Fragment fragment;
    private List<ReviewDTO> reviewList;

    private List<String> reviewImages;
    public ReviewAdapter(Fragment fragment, List<ReviewDTO> reviewList) {
        this.fragment = fragment;
        this.reviewList = reviewList;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, img1, img2, img3;
        TextView txtName, txtDate,txtComment, txtRating, txtRatingValue;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            txtName = itemView.findViewById(R.id.tvUserName);
            txtDate = itemView.findViewById(R.id.tvDate);
            txtComment = itemView.findViewById(R.id.tvReviewContent);
            txtRating = itemView.findViewById(R.id.tvUserRating);
            txtRatingValue = itemView.findViewById(R.id.tvRatingValue);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
        }
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false); // file XML bạn đã tạo
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

        // Gán rating
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < review.getRating(); i++) {
            stars.append("★ ");
        }
        holder.txtRating.setText(stars.toString().trim());

        holder.txtRatingValue.setText(String.valueOf(review.getRating())+".0");

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
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
