package com.example.hotel_project.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.1.18:8080/";

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Đường dẫn API
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển JSON thành Object
                    .build();
        }
        return retrofit;
    }
}
