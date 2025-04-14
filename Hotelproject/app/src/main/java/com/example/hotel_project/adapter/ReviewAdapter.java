package com.example.hotel_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.model.ReviewDTO;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Fragment fragment;
    private List<ReviewDTO> reviewList;

    public ReviewAdapter(Fragment fragment, List<ReviewDTO> reviewList) {
        this.fragment = fragment;
        this.reviewList = reviewList;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtComment, txtRating;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            txtName = itemView.findViewById(R.id.txt_name);
            txtComment = itemView.findViewById(R.id.txt_comment);
            txtRating = itemView.findViewById(R.id.txt_rating);
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

        // Gán rating
        holder.txtRating.setText("★ " + review.getRating());

        // Gán ảnh đại diện nếu có (hoặc dùng ảnh default)
        // Glide/Picasso nếu bạn có link avatar
        // Glide.with(holder.itemView.getContext()).load(review.getAccountReview().getAvatarUrl()).into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
