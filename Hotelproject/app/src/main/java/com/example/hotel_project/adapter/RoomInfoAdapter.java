package com.example.hotel_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomInfoAdapter extends RecyclerView.Adapter<RoomInfoAdapter.InfoViewHolder> {
    private List<String> infoTexts;
    private Map<String, Integer> infoIcons;

    public RoomInfoAdapter(List<String> infoTexts) {
        this.infoTexts = infoTexts;
        infoIcons = new HashMap<>();

        infoIcons.put("Bed", R.drawable.ic_bed);
        infoIcons.put("Bath", R.drawable.ic_bath);
        infoIcons.put("Deluxe", R.drawable.ic_room); // icon đại diện loại phòng, có thể tùy chỉnh
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        String text = infoTexts.get(position);
        holder.infoText.setText(text);

        // Tùy vào text, lấy icon tương ứng
        Integer icon = null;
        if (text.contains("Bed")) icon = infoIcons.get("Bed");
        else if (text.contains("Bath")) icon = infoIcons.get("Bath");
        else icon = infoIcons.get("Deluxe");

        if (icon != null) {
            holder.infoIcon.setImageResource(icon);
        }
    }

    @Override
    public int getItemCount() {
        return infoTexts.size();
    }

    public static class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView infoText;
        ImageView infoIcon;

        public InfoViewHolder(View itemView) {
            super(itemView);
            infoText = itemView.findViewById(R.id.infoText);
            infoIcon = itemView.findViewById(R.id.infoIcon);
        }
    }
}

