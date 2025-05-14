package com.example.hotel_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.model.BookingScheduleDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingScheduleAdapter extends RecyclerView.Adapter<BookingScheduleAdapter.BookingScheduleViewHolder>{
    private List<BookingScheduleDTO> bookingSchedules;
    private Context context;

    public BookingScheduleAdapter(Context context, List<BookingScheduleDTO> bookingSchedules) {
        this.context = context;
        this.bookingSchedules = bookingSchedules;
    }

    @Override
    public BookingScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_upcoming, parent, false);
        return new BookingScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingScheduleViewHolder holder, int position) {
        BookingScheduleDTO bookingSchedule = bookingSchedules.get(position);
        holder.txtHotelName.setText(bookingSchedule.getHotel().getName());
        holder.txtRoomType.setText(bookingSchedule.getRoom().getRoomType());
        holder.txtAddress.setText(bookingSchedule.getHotel().getAddress() + " - " + bookingSchedule.getHotel().getCity());

        LocalDateTime dateStart = bookingSchedule.getDateStart();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dateStart.format(outputFormatter);
        holder.txtCheckIn.setText("Checkin: " + formattedDate);


        String fullUrl = RetrofitClient.IMG_URL
                + bookingSchedule.getHotel().getHotel_image_url()
                + "?v=" + System.currentTimeMillis();
        Glide.with(context)
                .load(fullUrl)
                .into(holder.imgHotel);

    }

    @Override
    public int getItemCount() {
        return bookingSchedules.size();
    }

    public static class BookingScheduleViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHotel;
        TextView txtHotelName, txtRoomType, txtAddress, txtCheckIn;

        public BookingScheduleViewHolder(View itemView) {
            super(itemView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            txtRoomType = itemView.findViewById(R.id.txtRoomType);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtCheckIn = itemView.findViewById(R.id.txtCheckIn);
            imgHotel = itemView.findViewById(R.id.imgHotel);
        }
    }
}
