package com.example.hotel_project.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static final String BASE_URL = "http://192.168.1.18:8080/";
    public static final String IMG_URL = "http://192.168.1.18:8080";
//    private static final String BASE_URL = "http://192.168.52.124:8080/";


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class,
                            (JsonDeserializer<LocalDate>)
                                    (json, type, context) -> LocalDate.parse(json.getAsString()))
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Đường dẫn API
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Chuyển JSON thành Object
                    .build();
        }
        return retrofit;
    }


}
