package com.example.hotel_project.retrofit;

import com.example.hotel_project.adapter.LocalDateTimeAdapter;
import com.example.hotel_project.adapter.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
//    public static final String BASE_URL = "http://192.168.1.18:8080/";
//    public static final String IMG_URL = "http://192.168.1.18:8080";

//    public static final String BASE_URL = "http://192.168.100.20:8080/";
//    public static final String IMG_URL = "http://192.168.100.20:8080";

//    public static final String BASE_URL = "http://172.20.10.5:8080/";
//    public static final String IMG_URL = "http://172.20.10.5:8080";
    public static final String BASE_URL = "http://172.16.31.50:8080/";
    public static final String IMG_URL = "http://172.16.31.50:8080";

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Đường dẫn API
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Chuyển JSON thành Object
                    .build();
        }
        return retrofit;
    }


}
