package com.example.hotel_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.activity.BookingOrderDetailActivity;
import com.example.hotel_project.activity.HotelDetailActivity;
import com.example.hotel_project.activity.MyHistoryActivity;
import com.example.hotel_project.api.BookingOrderService;
import com.example.hotel_project.model.BookingOrderDTO;
import com.example.hotel_project.model.BookingScheduleDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingScheduleAdapter extends RecyclerView.Adapter<BookingScheduleAdapter.BookingScheduleViewHolder>{
    private List<BookingScheduleDTO> bookingSchedules;
    private Context context;

    private BookingOrderDTO orders;

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

        holder.itemView.setOnClickListener(v -> {
            BookingOrderService api = RetrofitClient.getRetrofit().create(BookingOrderService.class);
            Call<BookingOrderDTO> call = api.getBookingOrderById(bookingSchedule.getIdBookingOrder());
            call.enqueue(new Callback<BookingOrderDTO>() {
                @Override
                public void onResponse(Call<BookingOrderDTO> call, Response<BookingOrderDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        BookingOrderDTO orders = response.body();

                        Intent intent = new Intent(context, BookingOrderDetailActivity.class);
                        intent.putExtra("bookingOrderDTO", orders);
                        intent.putExtra("roomDTO", bookingSchedule.getRoom());
                        intent.putExtra("hotelDTO", bookingSchedule.getHotel());
                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BookingOrderDTO> call, Throwable t) {
                    Log.e("API_ERROR", "Lỗi gọi API: " + t.getMessage());
                    Toast.makeText(context, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                }
            });
        });
        holder.btnCancel.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Cancel Booking")
                    .setMessage("Are you sure you want to cancel this booking?")
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        cancelBooking(bookingSchedule.getIdBookingOrder(), position); // Gọi hàm thực hiện hủy
                    })
                    .setNegativeButton("No", null)
                    .show();
             });
    }

    private void cancelBooking(String orderId, int position){
        BookingOrderService  bookingOrderService = RetrofitClient.getRetrofit().create(BookingOrderService.class);
        Call<Boolean> call = bookingOrderService.updateBookingStatus(orderId, false);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toast.makeText(context, "Booking has been cancelled.", Toast.LENGTH_SHORT).show();
                    bookingSchedules.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, bookingSchedules.size());
                } else {
                    Toast.makeText(context, "Failed to cancel booking.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(context, "Booking has been cancelled.", Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getItemCount() {
        return bookingSchedules.size();
    }

    public static class BookingScheduleViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHotel;
        TextView txtHotelName, txtRoomType, txtAddress, txtCheckIn;
        Button btnCancel;

        public BookingScheduleViewHolder(View itemView) {
            super(itemView);
            txtHotelName = itemView.findViewById(R.id.txtHotelName);
            txtRoomType = itemView.findViewById(R.id.txtRoomType);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtCheckIn = itemView.findViewById(R.id.txtCheckIn);
            imgHotel = itemView.findViewById(R.id.imgHotel);
            btnCancel = itemView.findViewById(R.id.btnCancel);

        }
    }

}
