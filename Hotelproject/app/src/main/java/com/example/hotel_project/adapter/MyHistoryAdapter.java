package com.example.hotel_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.activity.HotelDetailActivity;
import com.example.hotel_project.activity.MyHistoryActivity;
import com.example.hotel_project.activity.WriteReviewActivity;
import com.example.hotel_project.model.BookingOrderDTO;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.HotelViewHolder>{
    private List<BookingOrderDTO> bookingOrderDTO;
    private Context context;

    public MyHistoryAdapter(Context context, List<BookingOrderDTO> bookingOrderDTO) {
        this.context = context;
        this.bookingOrderDTO = bookingOrderDTO;
    }

    @NonNull
    @Override
    public MyHistoryAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_history, parent, false);
        return new MyHistoryAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHistoryAdapter.HotelViewHolder holder, int position) {
        BookingOrderDTO bookingOrder= bookingOrderDTO.get(position);
        holder.textName.setText(bookingOrder.getHotelOrder().getName());
        holder.textAddress.setText(bookingOrder.getHotelOrder().getAddress() + ", " + bookingOrder.getHotelOrder().getCity());

        String dateStartStr = bookingOrder.getDateStart();  // Giả sử đây là chuỗi ngày như "2025-04-01T14:00:00"
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(dateStartStr, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dateStart.format(outputFormatter);
        holder.textDateStart.setText("Checkin: " + formattedDate);

        String dateStartEnd = bookingOrder.getDateEnd();  // Giả sử đây là chuỗi ngày như "2025-04-01T14:00:00"
        DateTimeFormatter inputFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateEnd = LocalDateTime.parse(dateStartEnd, inputFormatter1);

        DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate1 = dateEnd.format(outputFormatter1);
        holder.textDateEnd.setText("Checkout: " + formattedDate1);



        // Load ảnh từ URL
        String fullUrl = RetrofitClient.IMG_URL
                + bookingOrder.getHotelOrder().getHotel_image_url()
                + "?v=" + System.currentTimeMillis();
        Glide.with(context)
                .load(fullUrl)
                .into(holder.imageHotel);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetailActivity.class);
            intent.putExtra("hotel_id", bookingOrder.getHotelId());
            context.startActivity(intent);
        });

        holder.btnAddReview.setOnClickListener(v -> {
            Intent intent = new Intent(context, WriteReviewActivity.class);
            intent.putExtra("bookingOrder", bookingOrder);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookingOrderDTO.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHotel;
        TextView textName, textAddress, textDateStart, textDateEnd;
        Button btnBook, btnAddReview;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHotel = itemView.findViewById(R.id.imageHotel);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
            btnBook = itemView.findViewById(R.id.btnBook);
            btnAddReview = itemView.findViewById(R.id.btnAddReview);
            textDateStart = itemView.findViewById(R.id.textCheckIn);
            textDateEnd = itemView.findViewById(R.id.textCheckOut);
        }
    }

}
