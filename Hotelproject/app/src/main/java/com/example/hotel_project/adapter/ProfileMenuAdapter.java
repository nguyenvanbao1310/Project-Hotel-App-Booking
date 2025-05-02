package com.example.hotel_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotel_project.R;
import com.example.hotel_project.model.ProfileMenuItem;

import java.util.List;

public class ProfileMenuAdapter extends BaseAdapter {
    private Context context;
    private List<ProfileMenuItem> menuItems;

    public ProfileMenuAdapter(Context context, List<ProfileMenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public ProfileMenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView icon;
        TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_profile_menu, parent, false);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.menuIcon);
            holder.text = convertView.findViewById(R.id.menuText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProfileMenuItem item = getItem(position);
        holder.icon.setImageResource(item.getIconRes());
        holder.text.setText(item.getTitle());

        return convertView;
    }
}
