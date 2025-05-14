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
import com.example.hotel_project.model.BookingOrderDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CancelledBookingAdapter extends RecyclerView.Adapter<CancelledBookingAdapter.HotelViewHolder>{
    private List<BookingOrderDTO> bookingOrderDTO;
    private Context context;

    public CancelledBookingAdapter(Context context, List<BookingOrderDTO> bookingOrderDTO) {
        this.context = context;
        this.bookingOrderDTO = bookingOrderDTO;
    }

    @NonNull
    @Override
    public CancelledBookingAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cancelled_booking, parent, false);
        return new CancelledBookingAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelledBookingAdapter.HotelViewHolder holder, int position) {
        BookingOrderDTO bookingOrder= bookingOrderDTO.get(position);
        holder.textName.setText(bookingOrder.getHotelOrder().getName());
        holder.textAddress.setText(bookingOrder.getHotelOrder().getAddress());
        holder.textHotelCity.setText(bookingOrder.getHotelOrder().getCity());

        String dateStartStr = bookingOrder.getDateStart();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(dateStartStr, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dateStart.format(outputFormatter);
        holder.textDateStart.setText("Checkin: " + formattedDate);

        String dateStartEnd = bookingOrder.getDateEnd();
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

    }

    @Override
    public int getItemCount() {
        return bookingOrderDTO.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHotel;
        TextView textName, textHotelCity, textDuration, textAddress, textDateStart, textDateEnd;
        Button btnBookAgian;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHotel = itemView.findViewById(R.id.imgHotel);
            textName = itemView.findViewById(R.id.txtHotelName);
            textHotelCity = itemView.findViewById(R.id.txtHotelCity);
            textAddress = itemView.findViewById(R.id.txtAddress);
            textDateStart = itemView.findViewById(R.id.txtCheckIn);
            textDateEnd = itemView.findViewById(R.id.txtCheckOut);
            textDuration = itemView.findViewById(R.id.txtDuration);
            btnBookAgian = itemView.findViewById(R.id.btnBookAgain);
        }
    }
}
