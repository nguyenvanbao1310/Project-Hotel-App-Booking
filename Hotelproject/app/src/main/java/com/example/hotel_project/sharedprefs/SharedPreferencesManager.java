package com.example.hotel_project.sharedprefs;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.google.gson.Gson;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "user_prefs";
    private static SharedPreferences sharedPreferences;

    public static void saveGuestDTO(Context context, GuestDTO guestDTO) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String guestJson = gson.toJson(guestDTO);
        editor.putString("guestDTO", guestJson);
        editor.apply();
    }

    public static GuestDTO getGuestDTO(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String guestJson = sharedPreferences.getString("guestDTO", null);
        Gson gson = new Gson();
        return gson.fromJson(guestJson, GuestDTO.class);
    }

    public static void saveAccountDTO(Context context, AccountDTO accountDTO) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String accountJson = gson.toJson(accountDTO);
        editor.putString("accountDTO", accountJson);
        editor.apply();
    }

    public static AccountDTO getAccountDTO(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("accountDTO", null);
        Gson gson = new Gson();
        return gson.fromJson(accountJson, AccountDTO.class);
    }

    public static void clear(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public static void saveNotificationState(Context context, boolean isEnabled) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notif_enabled", isEnabled);
        editor.apply();
    }

    public static boolean getNotificationState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("notif_enabled", true); // mặc định bật = true
    }

}

