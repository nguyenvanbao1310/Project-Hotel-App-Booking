package com.example.hotel_project.sharedprefs;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.google.gson.Gson;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_KEEP_ME_LOGIN = "keep_me_login";

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
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveLoginInfo(Context context, String email, String password, boolean keepMeLogin) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        if (keepMeLogin) {
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.putBoolean(KEY_KEEP_ME_LOGIN, true);
        } else {
            editor.remove(KEY_EMAIL);
            editor.remove(KEY_PASSWORD);
            editor.putBoolean(KEY_KEEP_ME_LOGIN, false);
        }
        editor.apply();
    }

    public static String getSavedEmail(Context context) {
        return getPrefs(context).getString(KEY_EMAIL, "");
    }

    public static String getSavedPassword(Context context) {
        return getPrefs(context).getString(KEY_PASSWORD, "");
    }

    public static boolean isKeepMeLogin(Context context) {
        return getPrefs(context).getBoolean(KEY_KEEP_ME_LOGIN, false);
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

