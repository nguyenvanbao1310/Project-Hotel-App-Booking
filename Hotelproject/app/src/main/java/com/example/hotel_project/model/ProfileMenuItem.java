package com.example.hotel_project.model;

public class ProfileMenuItem {
    private int iconRes;
    private String title;

    public ProfileMenuItem(int iconRes, String title) {
        this.iconRes = iconRes;
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public String getTitle() {
        return title;
    }
}
