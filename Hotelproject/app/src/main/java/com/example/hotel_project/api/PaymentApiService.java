package com.example.hotel_project.api;

import com.example.hotel_project.model.PaymentDTO;
import com.example.hotel_project.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentApiService {
    @POST("api/payments/create_payment_url")
    Call<ResponseObject<String>> createVNPayUrl(@Body PaymentDTO paymentDTO);
}

