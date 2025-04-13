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

public class ExtensionAdapter extends RecyclerView.Adapter<ExtensionAdapter.ExtensionViewHolder> {
    private List<String> extensions;

    // Dữ liệu icon cho từng extension
    private Map<String, Integer> extensionIcons;

    public ExtensionAdapter(List<String> extensions) {
        this.extensions = extensions;
        // Khởi tạo map để ánh xạ extension với icon
        extensionIcons = new HashMap<>();
        extensionIcons.put("TV", R.drawable.ic_tv); // Gán icon TV
        extensionIcons.put("Air conditioning", R.drawable.ic_air_conditioning); // Gán icon điều hòa
        extensionIcons.put("Flat-screen TV", R.drawable.ic_flat_screen_tv); // Gán icon TV màn hình phẳng
        extensionIcons.put("Free Wifi", R.drawable.ic_wifi); // Gán icon WiFi
        extensionIcons.put("Free toiletries", R.drawable.ic_toiletries); // Gán icon đồ vệ sinh miễn phí
        extensionIcons.put("Hairdryer", R.drawable.ic_hairdryer); // Gán icon máy sấy tóc
    }

    @Override
    public ExtensionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extension, parent, false);
        return new ExtensionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExtensionViewHolder holder, int position) {
        String extensionText = extensions.get(position);
        holder.extensionText.setText(extensionText);

        // Lấy icon từ Map và set vào ImageView
        Integer iconResId = extensionIcons.get(extensionText);
        if (iconResId != null) {
            holder.extensionIcon.setImageResource(iconResId);
        }
    }

    @Override
    public int getItemCount() {
        return extensions.size();
    }

    public static class ExtensionViewHolder extends RecyclerView.ViewHolder {
        TextView extensionText;
        ImageView extensionIcon;

        public ExtensionViewHolder(View itemView) {
            super(itemView);
            extensionText = itemView.findViewById(R.id.extensionText);
            extensionIcon = itemView.findViewById(R.id.extensionIcon);
        }
    }
}
