package com.example.hotel_project.adapter;

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
import com.example.hotel_project.dialog.BookingDialog;
import com.example.hotel_project.dialog.RoomDescriptionDialog;
import com.example.hotel_project.model.RoomDTO;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Fragment fragment;
    private List<RoomDTO> roomList;

    public RoomAdapter(Fragment fragment, List<RoomDTO> roomList) {
        this.fragment = fragment;
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

            RoomDescriptionDialog dialog = new RoomDescriptionDialog(imageUrls, extension);
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
        BookingDialog dialog = BookingDialog.newInstance(room);

        dialog.setBookingListener(message -> {
            Toast.makeText(fragment.getContext(), message, Toast.LENGTH_LONG).show();
        });

        dialog.show(fragment.getParentFragmentManager(), "BookingDialog");
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
