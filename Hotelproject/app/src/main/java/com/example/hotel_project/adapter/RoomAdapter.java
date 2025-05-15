package com.example.hotel_project.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.api.BookingScheduleService;
import com.example.hotel_project.dialog.BookingDialog;
import com.example.hotel_project.dialog.RoomDescriptionDialog;
import com.example.hotel_project.model.BookingScheduleDTO;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.RoomDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Fragment fragment;
    private List<RoomDTO> roomList;

    private Hotel hotel;

    public RoomAdapter(Fragment fragment, Hotel hotel, List<RoomDTO> roomList) {
        this.fragment = fragment;
        this.hotel = hotel;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomDTO room = roomList.get(position);
        holder.textRoomType.setText(room.getRoomType());
        holder.textPrice.setText(String.valueOf(room.getPriceByDay()));

        holder.buttonDescription.setOnClickListener(v -> {
            List<String> imageUrls = room.getImages(); // giả sử mỗi room có list ảnh
            List<String> extension = room.getExtension();   // mô tả phòng

            RoomDescriptionDialog dialog = new RoomDescriptionDialog(room, imageUrls, extension);
            dialog.show(fragment.getParentFragmentManager(), "RoomDescriptionDialog");
        });

        holder.buttonBook.setOnClickListener(v -> {
            showBookingDialog(room);
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    private void showBookingDialog(RoomDTO room) {

        BookingScheduleService apiService = RetrofitClient.getRetrofit().create(BookingScheduleService.class);
        Call<List<BookingScheduleDTO>> call = apiService.getBookingSchedulesByRoomId(room.getId());
        call.enqueue(new Callback<List<BookingScheduleDTO>>() {
            @Override
            public void onResponse(Call<List<BookingScheduleDTO>> call, Response<List<BookingScheduleDTO>> response) {
                if (response.isSuccessful()) {
                    List<BookingScheduleDTO> bookingScheduleDTO = response.body();
                    BookingDialog dialog = BookingDialog.newInstance(hotel, room, bookingScheduleDTO );
                    dialog.setBookingListener(message -> {
                        Toast.makeText(fragment.getContext(), message, Toast.LENGTH_LONG).show();
                    });

                    dialog.show(fragment.getParentFragmentManager(), "BookingDialog");
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e("BookingSchedule", "Error response: " + response.code() + " - " + errorBody);
                    } catch (IOException e) {
                        Log.e("BookingSchedule", "Error parsing errorBody", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookingScheduleDTO>> call, Throwable t) {
                Log.e("BookingSchedule", "API Failure: ", t);
            }
        });


    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView textRoomType, textPrice;
        LinearLayout buttonDescription;
        Button buttonBook;

        public RoomViewHolder(View itemView) {
            super(itemView);
            textRoomType = itemView.findViewById(R.id.textRoomType);
            textPrice = itemView.findViewById(R.id.textPrice);
            buttonDescription = itemView.findViewById(R.id.btnDescription);
            buttonBook = itemView.findViewById(R.id.buttonBook);
        }
    }
}
