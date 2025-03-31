package com.example.hotel_project.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient
{
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/user/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(BASE_URL);
            builder.addConverterFactory(GsonConverterFactory.create());
            retrofit = builder
                    .build();
        }
        return retrofit;
    }
}
